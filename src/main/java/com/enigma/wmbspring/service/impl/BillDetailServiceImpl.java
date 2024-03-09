package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.entity.BillDetail;
import com.enigma.wmbspring.repository.BillDetailRepository;
import com.enigma.wmbspring.service.BillDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailRepository billDetailRepository;
    @Override
    public List<BillDetail> createBulk(List<BillDetail> billDetails) {
        return billDetailRepository.saveAllAndFlush(billDetails);
    }
}
