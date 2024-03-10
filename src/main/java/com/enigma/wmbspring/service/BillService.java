package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.dto.request.SearchBillRequest;
import com.enigma.wmbspring.dto.response.BillResponse;
import com.enigma.wmbspring.entity.Bill;
import org.springframework.data.domain.Page;

public interface BillService {
    BillResponse create(BillRequest request);
    Page<BillResponse> getAll(SearchBillRequest request);
}
