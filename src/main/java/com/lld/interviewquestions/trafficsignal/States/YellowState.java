package com.lld.interviewquestions.trafficsignal.States;

import com.lld.interviewquestions.trafficsignal.TrafficSignal;

public class YellowState extends SignalState {

    public YellowState() {
        System.out.println("Traffic signal is YELLOW. Prepare to stop.");
    }

    @Override
    public void change(TrafficSignal trafficSignal) {
        System.out.println("Changing from YELLOW to RED...");
        trafficSignal.setSignalState(new RedState());
    }

    @Override
    public void displaySignal() {
        System.out.println("[YELLOW] - Slow down and prepare to stop.");
    }
}
