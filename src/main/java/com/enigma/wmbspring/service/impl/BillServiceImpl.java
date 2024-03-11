package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.ResponseMessage;
import com.enigma.wmbspring.constant.TransType;
import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.dto.request.SearchBillRequest;
import com.enigma.wmbspring.dto.request.UpdateTransactionStatusRequest;
import com.enigma.wmbspring.dto.response.BillDetailResponse;
import com.enigma.wmbspring.dto.response.BillResponse;
import com.enigma.wmbspring.dto.response.PaymentResponse;
import com.enigma.wmbspring.entity.*;
import com.enigma.wmbspring.repository.BillRepository;
import com.enigma.wmbspring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final CustomerService customerService;
    private final MenuService menuService;
    private final BillDetailService billDetailService;
    private final TablesService tablesService;
    private final TransactionTypeService transactionTypeService;
    private final PaymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillResponse create(BillRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        TransactionType transactionType = transactionTypeService.getByName(request.getTransType());
        Tables tableName;
        if (request.getTransType().equals("TA")) {
             tableName = null;
        } else {
             tableName = tablesService.getByName(request.getTableName());
        }

        Bill bill = Bill.builder()
                .customer(customer)
                .transDate(new Date())
                .table(tableName)
                .transactionType(transactionType)
                .build();
        billRepository.saveAndFlush(bill);

        List<BillDetail> billDetails = request.getBillDetails().stream()
                .map(detailRequest -> {
                    Menu menu = menuService.getById(detailRequest.getMenuId());

                    return BillDetail.builder()
                            .menu(menu)
                            .bill(bill)
                            .qty(detailRequest.getQty())
                            .price(menu.getPrice())
                            .build();
                }).toList();

        bill.setBillDetails(billDetails);
        billDetailService.createBulk(billDetails);

        List<BillDetailResponse> billDetailResponses = billDetails.stream().map(detail ->
                BillDetailResponse.builder()
                        .id(detail.getId())
                        .menuId(detail.getMenu().getId())
                        .menuPrice(detail.getPrice())
                        .quantity(detail.getQty())
                        .build()).toList();

        Payment payment = paymentService.createPayment(bill);
        bill.setPayment(payment);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getTransactionStatus())
                .build();

        return BillResponse.builder()
                .id(bill.getId())
                .customerId(bill.getCustomer().getId())
                .transDate(bill.getTransDate())
                .billDetails(billDetailResponses)
                .paymentResponse(paymentResponse)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BillResponse> getAll(SearchBillRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Bill> bills = billRepository.findAll(pageable);

        return bills.map(bill -> {
            List<BillDetailResponse> billDetailResponses = bill.getBillDetails().stream().map(detail -> BillDetailResponse.builder()
                    .id(detail.getId())
                    .menuId(detail.getMenu().getId())
                    .menuPrice(detail.getPrice())
                    .quantity(detail.getQty())
                    .build()).toList();

            return BillResponse.builder()
                    .id(bill.getId())
                    .customerId(bill.getCustomer().getId())
                    .transDate(bill.getTransDate())
                    .billDetails(billDetailResponses)
                    .build();
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatus(UpdateTransactionStatusRequest request) {
        Bill bill = billRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
        Payment payment = bill.getPayment();
        payment.setTransactionStatus(request.getTransactionStatus());
    }
}
