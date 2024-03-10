package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.entity.Customer;
import com.enigma.wmbspring.entity.UserAccount;
import com.enigma.wmbspring.repository.CustomerRepository;
import com.enigma.wmbspring.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void shouldReturnCustomerWhenCreate() {
        // given
        Customer parameterCustomer = Customer.builder()
                .id("cus-1")
                .name("Paul")
                .phone_number("084567454")
                .status(true)
                .userAccount(UserAccount.builder().build())
                .build();

        // Stubbing config
        Mockito.when(customerRepository.saveAndFlush(parameterCustomer))
                .thenReturn(parameterCustomer);

        Assertions.assertNotNull(customerService.create(parameterCustomer));
    }
}
