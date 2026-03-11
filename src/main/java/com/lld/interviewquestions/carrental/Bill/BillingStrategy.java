package com.lld.interviewquestions.carrental.Bill;
import com.lld.interviewquestions.carrental.reservation.Reservation;

public interface BillingStrategy {

    Bill generateBill(Reservation reservation);
}
