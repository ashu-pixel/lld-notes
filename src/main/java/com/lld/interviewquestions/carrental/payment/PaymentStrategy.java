package com.lld.interviewquestions.carrental.payment;

import com.lld.interviewquestions.carrental.Bill.Bill;

public interface PaymentStrategy {

    Payment processPayment(Bill bill, double paymentAmount);
}

