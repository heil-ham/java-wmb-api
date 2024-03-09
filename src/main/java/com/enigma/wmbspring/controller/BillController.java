package com.enigma.wmbspring.controller;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.entity.Bill;
import com.enigma.wmbspring.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping
    public Bill createNewBill(@RequestBody BillRequest request) {
        return billService.create(request);
    }

}
