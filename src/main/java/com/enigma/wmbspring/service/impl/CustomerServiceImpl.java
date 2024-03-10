package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.ResponseMessage;
import com.enigma.wmbspring.dto.request.SearchCustomerRequest;
import com.enigma.wmbspring.dto.request.UpdateCustomerRequest;
import com.enigma.wmbspring.dto.response.CustomerResponse;
import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.entity.UserAccount;
import com.enigma.wmbspring.repository.CustomerRepository;
import com.enigma.wmbspring.service.CustomerService;
import com.enigma.wmbspring.service.UserService;
import com.enigma.wmbspring.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
//    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getOneByid(String id) {
        return convertCustomerToCustomerResponse(findByIdOrThrownNotFound(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        return optionalCustomer.get();

    }

    @Transactional(readOnly = true)
    @Override
    public Page<Customer> getAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<Customer> specification = CustomerSpecification.getSpecification(request);
        return customerRepository.findAll(specification,pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        Customer currentCustomer = findByIdOrThrownNotFound(request.getId());
//        UserAccount userAccount = userService.getByContext();

//        if (!userAccount.getId().equals(currentCustomer.getUserAccount().getId())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ResponseMessage.ERROR_FORBIDDEN);
//        }

        currentCustomer.setName(request.getName());
        currentCustomer.setPhone_number(request.getPhoneNumber());
        customerRepository.saveAndFlush(currentCustomer);
        return convertCustomerToCustomerResponse(currentCustomer);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Customer customer = findByIdOrThrownNotFound(id);
        customerRepository.delete(customer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrownNotFound(id);
        customerRepository.updateStatus(id, status);
    }

    public Customer findByIdOrThrownNotFound(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhone_number())
                .status(customer.getStatus())
                .userAccountId(customer.getUserAccount().getId())
                .build();
    }
}
