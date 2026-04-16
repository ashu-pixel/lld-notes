package com.lld.interviewquestions.vending_machine.vendingmachinestates.impl;

import java.util.ArrayList;

import com.lld.interviewquestions.vending_machine.context.Item;
import com.lld.interviewquestions.vending_machine.context.VendingMachine;
import com.lld.interviewquestions.vending_machine.vendingmachinestates.State;

public class IdleState extends State {

    public IdleState(){
        System.out.println("Currently Vending machine is in IdleState");
    }

    public IdleState(VendingMachine machine){
        System.out.println("Currently Vending machine is in IdleState");
        machine.setCoinList(new ArrayList<>());
    }

    @Override
    public void clickOnInsertCoinButton(VendingMachine machine) throws Exception{
        machine.setVendingMachineState(new HasMoneyState());
    }

    @Override
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception {
        machine.getInventory().addItem(item, codeNumber);
    }
}
