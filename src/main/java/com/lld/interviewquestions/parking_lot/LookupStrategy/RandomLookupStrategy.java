package com.lld.interviewquestions.parking_lot.LookupStrategy;

import java.util.List;

import com.lld.interviewquestions.parking_lot.Entity.ParkingSpot;

public class RandomLookupStrategy implements ParkingSpotLookupStrategy {

    @Override
    public ParkingSpot selectSpot(List<ParkingSpot> spots) {
        for(ParkingSpot spot : spots) {
            if(spot.isSpotFree()) {
                return spot;
            }
        }
        return null;
    }
}


