package com.lld.interviewquestions.carrental.Bill;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.lld.interviewquestions.carrental.product.Vehicle;
import com.lld.interviewquestions.carrental.product.VehicleInventoryManager;
import com.lld.interviewquestions.carrental.reservation.Reservation;


public class DailyBillingStrategy implements BillingStrategy {

    VehicleInventoryManager vehicleInventoryManager;

    private final AtomicInteger billIdGenerator = new AtomicInteger(5000);

    public DailyBillingStrategy(VehicleInventoryManager vehicleInventoryManager) {
        this.vehicleInventoryManager = vehicleInventoryManager;
    }

    @Override
    public Bill generateBill(Reservation reservation) {

        long days = ChronoUnit.DAYS.between(
                reservation.getDateBookedFrom(),
                reservation.getDateBookedTo()
        ) + 1;

        Vehicle vehicle = vehicleInventoryManager.getVehicle(reservation.getVehicleId()).get();
        double rate = vehicle.getDailyRentalCost();

        double total = days * rate;

        return new Bill(
                billIdGenerator.getAndIncrement(),
                reservation.getReservationId(),
                total
        );
    }
}
