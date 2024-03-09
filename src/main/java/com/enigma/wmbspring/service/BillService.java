package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.entity.Bill;

public interface BillService {
    Bill create(BillRequest request);
}
