package com.enigma.wmbspring.repository;

import com.enigma.wmbspring.constant.TransType;
import com.enigma.wmbspring.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, String> {
    TransactionType findByTransType(TransType transType);
}
