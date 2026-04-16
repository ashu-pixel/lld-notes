package com.lld.interviewquestions.trafficsignal;

import com.lld.interviewquestions.trafficsignal.States.RedState;
import com.lld.interviewquestions.trafficsignal.States.SignalState;

public class TrafficSignal {

    private SignalState currentState;

    public TrafficSignal() {
        setSignalState(new RedState());
    }

    public void setSignalState(SignalState signalState) {
        this.currentState = signalState;
    }

    public void change() {
        currentState.change(this);
    }

    public void showSignal() {
        currentState.displaySignal();
    }
}
