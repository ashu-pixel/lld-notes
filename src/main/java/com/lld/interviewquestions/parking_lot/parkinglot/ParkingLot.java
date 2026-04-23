package com.lld.interviewquestions.parking_lot.parkinglot;

import com.lld.interviewquestions.parking_lot.Entity.Ticket;
import com.lld.interviewquestions.parking_lot.Entity.Vehicle;
import com.lld.interviewquestions.parking_lot.payment.Payment;

public class ParkingLot {

    private final ParkingBuilding building;
    private final EntranceGate entranceGate;
    private final ExitGate exitGate;

    public ParkingLot(ParkingBuilding building,
                      EntranceGate entranceGate,
                      ExitGate exitGate) {
        this.building = building;
        this.entranceGate = entranceGate;
        this.exitGate = exitGate;
    }

    public Ticket vehicleArrives(Vehicle vehicle) {
        return entranceGate.enter(building, vehicle);
    }

    public void vehicleExits(Ticket ticket, Payment payment) {
        exitGate.completeExit(building, ticket, payment);
    }
}
