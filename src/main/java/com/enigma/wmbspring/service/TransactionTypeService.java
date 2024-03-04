package com.enigma.wmbspring.service;

import com.enigma.wmbspring.constant.TransType;
import com.enigma.wmbspring.entity.TransactionType;

public interface TransactionTypeService {
    TransactionType getOrSave(TransType type);
}
