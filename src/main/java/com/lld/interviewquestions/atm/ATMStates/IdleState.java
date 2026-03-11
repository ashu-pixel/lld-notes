package com.lld.interviewquestions.atm.ATMStates;

import com.lld.interviewquestions.atm.ATMRoomComponents.ATM;
import com.lld.interviewquestions.atm.ATMRoomComponents.Card;

public class IdleState extends ATMState {

    @Override
    public void insertCard(ATM atm, Card card) {
        System.out.println("Card is inserted");
        atm.setCurrentATMState(new HasCardState());
    }
}