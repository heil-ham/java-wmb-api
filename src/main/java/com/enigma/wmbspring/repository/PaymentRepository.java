package com.enigma.wmbspring.repository;

import com.enigma.wmbspring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findAllByTransactionStatusIn(List<String> transactionStatus);
}
