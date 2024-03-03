package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.repository.CustomerRepository;
import com.enigma.wmbspring.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }
}
