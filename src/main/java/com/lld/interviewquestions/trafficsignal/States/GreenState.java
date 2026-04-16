package com.lld.interviewquestions.trafficsignal.States;

import com.lld.interviewquestions.trafficsignal.TrafficSignal;

public class GreenState extends SignalState {

    public GreenState() {
        System.out.println("Traffic signal is GREEN. Go.");
    }

    @Override
    public void change(TrafficSignal trafficSignal) {
        System.out.println("Changing from GREEN to YELLOW...");
        trafficSignal.setSignalState(new YellowState());
    }

    @Override
    public void displaySignal() {
        System.out.println("[GREEN] - Go if safe.");
    }
}
