package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.TransType;
import com.enigma.wmbspring.entity.TransactionType;
import com.enigma.wmbspring.repository.TransactionTypeRepository;
import com.enigma.wmbspring.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;
    @Override
    public TransactionType getByName(String type) {
        return transactionTypeRepository.findByTransType(TransType.valueOf(type));
    }
}
