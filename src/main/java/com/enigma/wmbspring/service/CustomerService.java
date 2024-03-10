package com.enigma.wmbspring.service;

import com.enigma.wmbspring.dto.request.SearchCustomerRequest;
import com.enigma.wmbspring.dto.request.UpdateCustomerRequest;
import com.enigma.wmbspring.dto.response.CustomerResponse;
import com.enigma.wmbspring.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer create(Customer customer);
    CustomerResponse getOneByid(String id);
    Customer getById(String id);
    Page<Customer> getAll(SearchCustomerRequest request);
    CustomerResponse update(UpdateCustomerRequest request);
    void deleteById(String id);
    void updateStatusById(String id, Boolean status);
}
