package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.dto.request.SearchCustomerRequest;
import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.repository.CustomerRepository;
import com.enigma.wmbspring.service.CustomerService;
import com.enigma.wmbspring.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        return optionalCustomer.get();

    }

    @Override
    public Page<Customer> getAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<Customer> specification = CustomerSpecification.getSpecification(request);
        return customerRepository.findAll(specification,pageable);
    }
}
