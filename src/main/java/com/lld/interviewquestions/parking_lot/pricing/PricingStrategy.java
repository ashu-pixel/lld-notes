package com.lld.interviewquestions.parking_lot.pricing;

import com.lld.interviewquestions.parking_lot.Ticket;

public interface PricingStrategy {

    double calculate(Ticket ticket);
}

