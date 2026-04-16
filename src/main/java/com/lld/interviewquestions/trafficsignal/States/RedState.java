package com.lld.interviewquestions.trafficsignal.States;

import com.lld.interviewquestions.trafficsignal.TrafficSignal;

public class RedState extends SignalState {

    public RedState() {
        System.out.println("Traffic signal is RED. Stop.");
    }

    @Override
    public void change(TrafficSignal trafficSignal) {
        System.out.println("Changing from RED to GREEN...");
        trafficSignal.setSignalState(new GreenState());
    }

    @Override
    public void displaySignal() {
        System.out.println("[RED] - Stop and wait.");
    }
}
