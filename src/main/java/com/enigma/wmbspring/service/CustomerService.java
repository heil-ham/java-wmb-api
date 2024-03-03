package com.enigma.wmbspring.service;

import com.enigma.wmbspring.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(String id);
}
