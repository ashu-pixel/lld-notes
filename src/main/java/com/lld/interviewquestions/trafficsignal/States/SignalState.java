package com.lld.interviewquestions.trafficsignal.States;

import com.lld.interviewquestions.trafficsignal.TrafficSignal;

public abstract class SignalState {

    public void change(TrafficSignal trafficSignal) {
        // default transition does nothing
    }

    public void displaySignal() {
        // default display does nothing
    }
}
