package com.lld.interviewquestions.elevator;

import com.lld.interviewquestions.elevator.enums.ElevatorDirection;

public class ExternalButton {

    private final ExternalDispatcher dispatcher;

    public ExternalButton(ExternalDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    // this direction of external button is only helpful in selecting the correct elevator
    public void pressButton(int floor, ElevatorDirection direction) {
        dispatcher.submitExternalRequest(floor, direction);
    }
}

