package com.enigma.wmbspring.controller;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.constant.ResponseMessage;
import com.enigma.wmbspring.dto.request.BillRequest;
import com.enigma.wmbspring.dto.request.SearchBillRequest;
import com.enigma.wmbspring.dto.response.BillResponse;
import com.enigma.wmbspring.dto.response.CommonResponse;
import com.enigma.wmbspring.dto.response.PagingResponse;
import com.enigma.wmbspring.entity.Bill;
import com.enigma.wmbspring.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<BillResponse>> createNewBill(@RequestBody BillRequest request) {
        BillResponse billResponse = billService.create(request);
        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(billResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<BillResponse>>> getAllBill(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        SearchBillRequest request = SearchBillRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<BillResponse> bills = billService.getAll(request);
        PagingResponse paging = PagingResponse.builder()
                .page(bills.getPageable().getPageNumber() + 1)
                .size(bills.getPageable().getPageSize())
                .totalPage(bills.getTotalPages())
                .totalElement(bills.getTotalElements())
                .hasNext(bills.hasNext())
                .hasPrevious(bills.hasPrevious())
                .build();
        CommonResponse<List<BillResponse>> response = CommonResponse.<List<BillResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(bills.getContent())
                .paging(paging)
                .build();
        return ResponseEntity.ok(response);
    }

}
