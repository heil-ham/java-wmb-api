package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.BillDetailRequest;
import com.enigma.wmbspring.entity.BillDetail;

import java.util.List;

public interface BillDetailService {
    List<BillDetail> createBulk(List<BillDetail> billDetails);
}
