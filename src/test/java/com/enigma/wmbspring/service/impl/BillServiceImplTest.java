package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.dto.request.BillDetailRequest;
import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.dto.response.BillDetailResponse;
import com.enigma.wmbspring.dto.response.BillResponse;
import com.enigma.wmbspring.entity.Bill;
import com.enigma.wmbspring.entity.BillDetail;
import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.entity.Menu;
import com.enigma.wmbspring.repository.BillRepository;
import com.enigma.wmbspring.service.BillService;
import com.enigma.wmbspring.service.TablesService;
import com.enigma.wmbspring.service.TransactionTypeService;
import org.apache.naming.TransactionRef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;
    @Mock
    private BillDetailServiceImpl billDetailService;
    @Mock
    private CustomerServiceImpl customerService;
    @Mock
    private MenuServiceImpl menuService;
    @Mock
    private TablesServiceImpl tablesService;
    @Mock
    private TransactionTypeServiceImpl transactionTypeService;
    private BillService billService;
    private BillRequest request;

    @BeforeEach
    void setUp() {
        billService = new BillServiceImpl(billRepository,customerService,menuService,billDetailService,tablesService,transactionTypeService);
    }

//    @Test
//    void shouldReturnBillWhenCreate() {
//        // Given
//        BillRequest billRequest = BillRequest.builder()
//                .customerId("cus-1")
//                .transType("TA")
//                .billDetails(List.of(
//                        BillDetailRequest.builder()
//                                .menuId("menu-1")
//                                .qty(1)
//                                .build(),
//                        BillDetailRequest.builder()
//                                .menuId("menu-2")
//                                .qty(2)
//                                .build()
//                ))
//                .build();
//
//        Customer mockCustomer = Customer.builder()
//                .id(billRequest.getCustomerId())
//                .build();
//
//        Mockito.when(customerService.getById(mockCustomer.getId()))
//                .thenReturn(mockCustomer);
//
//        Bill bill = Bill.builder()
//                .id("bill-1")
//                .customer(mockCustomer)
//                .transDate(new Date())
//                .build();
//
//
//        Mockito.when(billRepository.saveAndFlush(Mockito.any(Bill.class)))
//                .thenReturn(bill);
//
//        List<BillDetail> mockbillDetails = new ArrayList<>();
//
//        for (BillDetailRequest detailRequest : billRequest.getBillDetails()) {
//            Menu mockmenu = Menu.builder()
//                    .id(detailRequest.getMenuId())
//                    .price(100000L)
//                    .build();
//            Mockito.when(menuService.getById(detailRequest.getMenuId())).thenReturn(mockmenu);
//
//            int increment = 0;
//            BillDetail mockDetail = BillDetail.builder()
//                    .id("bill-" + increment++)
//                    .bill(bill)
//                    .menu(mockmenu)
//                    .qty(detailRequest.getQty())
//                    .price(mockmenu.getPrice())
//                    .build();
//            mockbillDetails.add(mockDetail);
//        }
//
//        Mockito.when(billDetailService.createBulk(Mockito.any()))
//                .thenReturn(mockbillDetails);
//
//        bill.setBillDetails(mockbillDetails);
//
//        BillResponse billResponse = billService.create(billRequest);
//
//        Assertions.assertEquals(2, billResponse.getBillDetails().size());
//    }
}
