package com.enigma.wmbspring.controller;

import com.enigma.wmbspring.constant.APIUrl;
import com.enigma.wmbspring.dto.request.SearchCustomerRequest;
import com.enigma.wmbspring.dto.response.CommonResponse;
import com.enigma.wmbspring.dto.response.PagingResponse;
import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CommonResponse<Customer>> createNewCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.create(customer);

        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully create new customer")
                .data(newCustomer)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> getCustomerId(@PathVariable String id) {
        Customer customer = customerService.getById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Customer>>> getAllCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phone_number", required = false) String phoneNumber,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .name(name)
                .phone_number(phoneNumber)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();

        Page<Customer> customers = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(customers.getTotalPages())
                .totalElement(customers.getTotalElements())
                .page(customers.getPageable().getPageNumber())
                .size(customers.getPageable().getPageSize())
                .hasNext(customers.hasNext())
                .hasPrevious(customers.hasPrevious())
                .build();

        CommonResponse<List<Customer>> response = CommonResponse.<List<Customer>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all customer")
                .data(customers.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
