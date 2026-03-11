package com.lld.interviewquestions.book_my_show.entities;

import java.util.UUID;

import com.lld.interviewquestions.book_my_show.enums.PaymentStatus;

public class Payment {

    private final UUID paymentId;
    private final PaymentStatus status;

    public Payment(PaymentStatus status) {
        this.paymentId = UUID.randomUUID();
        this.status = status;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
