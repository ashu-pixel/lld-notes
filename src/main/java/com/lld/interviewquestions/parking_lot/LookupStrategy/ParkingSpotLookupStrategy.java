package com.lld.interviewquestions.parking_lot.LookupStrategy;

import java.util.List;

import com.lld.interviewquestions.parking_lot.Entity.ParkingSpot;

public interface ParkingSpotLookupStrategy {

    ParkingSpot selectSpot(List<ParkingSpot> spots);
}


