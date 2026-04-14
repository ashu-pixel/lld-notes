package com.lld.interviewquestions.parking_lot.parkinglot;

import java.util.Map;
import java.util.Optional;

import com.lld.interviewquestions.parking_lot.Entity.ParkingSpot;
import com.lld.interviewquestions.parking_lot.enums.VehicleType;
import com.lld.interviewquestions.parking_lot.spotManagers.ParkingSpotManager;

public class ParkingLevel {

    private final int levelNumber;
    private final Map<VehicleType, ParkingSpotManager> managers;

    public ParkingLevel(int levelNumber,
            Map<VehicleType, ParkingSpotManager> managers) {
        this.levelNumber = levelNumber;
        this.managers = managers;
    }

    public boolean hasAvailability(VehicleType type) {
        ParkingSpotManager manager = managers.get(type);
        return manager != null && manager.hasFreeSpot();
    }

    public ParkingSpot park(VehicleType type) {
        ParkingSpotManager manager = managers.get(type);

        return Optional.ofNullable(manager)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No parking manager for vehicle type: " + type))
                .park();
    }

    public void unPark(VehicleType type, ParkingSpot spot) {
        ParkingSpotManager manager = managers.get(type);
        Optional.ofNullable(manager)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No parking manager for vehicle type: " + type))
                .unPark(spot);
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
