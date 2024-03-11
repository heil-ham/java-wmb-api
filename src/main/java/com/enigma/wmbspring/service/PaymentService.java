package com.enigma.wmbspring.service;

import com.enigma.wmbspring.entity.Bill;
import com.enigma.wmbspring.entity.Payment;

public interface PaymentService {
    Payment createPayment(Bill bill);
    void chekFailedAndUpdatePayments();
}
