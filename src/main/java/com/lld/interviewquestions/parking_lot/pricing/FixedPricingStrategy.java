package com.lld.interviewquestions.parking_lot.pricing;

import com.lld.interviewquestions.parking_lot.Entity.Ticket;

public class FixedPricingStrategy implements PricingStrategy {

    @Override
    public double calculate(Ticket ticket) {
        return 100;
    }
}

