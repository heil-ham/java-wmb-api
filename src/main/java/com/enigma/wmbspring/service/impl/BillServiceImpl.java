package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.entity.*;
import com.enigma.wmbspring.repository.BillRepository;
import com.enigma.wmbspring.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Bill create(BillRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Tables tableName = tablesService.getByName(request.getTableName());
        TransactionType transactionType = transactionTypeService.getByName(request.getTransType());


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
        return bill;
    }
}
