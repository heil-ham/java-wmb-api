package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.dto.request.PaymentDetailRequest;
import com.enigma.wmbspring.dto.request.PaymentItemDetailRequest;
import com.enigma.wmbspring.dto.request.PaymentRequest;
import com.enigma.wmbspring.entity.Bill;
import com.enigma.wmbspring.entity.BillDetail;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.entity.Payment;
import com.enigma.wmbspring.repository.PaymentRepository;
import com.enigma.wmbspring.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestClient restClient;
    private final String SECRET_KEY;
    private final String BASE_URL_SNAP;

    @Autowired
    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            RestClient restClient,
            @Value("${midtrans.api.key}") String SECRET_KEY,
            @Value("${midtrans.api.snap-url}")String BASE_URL_SNAP) {
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        this.SECRET_KEY = SECRET_KEY;
        this.BASE_URL_SNAP = BASE_URL_SNAP;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Payment createPayment(Bill bill) {
        long amount = bill.getBillDetails()
                .stream().mapToLong(value -> (value.getQty() * value.getPrice()))
                .reduce(0, Long::sum);

        List<PaymentItemDetailRequest> itemDetailRequestList = bill.getBillDetails().stream()
                .map(billDetail -> PaymentItemDetailRequest.builder()
                        .name(billDetail.getMenu().getName())
                        .price(billDetail.getPrice())
                        .quantity(billDetail.getQty())
                        .build()).toList();

        PaymentRequest request = PaymentRequest.builder()
                .paymentDetailRequest(PaymentDetailRequest.builder()
                        .orderId(bill.getId())
                        .amount(amount)
                        .build())
                .paymentItemDetails(itemDetailRequestList)
                .paymentMethod(List.of("shopeepay", "gopay"))
                .build();

        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(BASE_URL_SNAP)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + SECRET_KEY)
                .retrieve().toEntity(new ParameterizedTypeReference<>() {});

        Map<String, String> body = response.getBody();

        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus("ordered")
                .build();
        paymentRepository.saveAndFlush(payment);

        return payment;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void chekFailedAndUpdatePayments() {
        List<String> transactionStatus = List.of("deny", "cancel", "expire", "failure");
        List<Payment> payments = paymentRepository.findAllByTransactionStatusIn(transactionStatus);

        for (Payment payment : payments) {
            for (BillDetail billDetail : payment.getBill().getBillDetails()) {
                Menu menu = billDetail.getMenu();
            }
        }
    }
}
