package com.lld.interviewquestions.parking_lot.parkinglot;

import com.lld.interviewquestions.parking_lot.Ticket;
import com.lld.interviewquestions.parking_lot.Entity.Vehicle;

public class EntranceGate {

    public Ticket enter(ParkingBuilding building, Vehicle vehicle) {
        return building.allocate(vehicle);
    }
}


