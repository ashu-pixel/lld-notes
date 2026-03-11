package com.lld.behavioralpatterns.state.vendingmachinestates.impl;

import java.util.ArrayList;

import com.lld.behavioralpatterns.state.context.Item;
import com.lld.behavioralpatterns.state.context.VendingMachine;
import com.lld.behavioralpatterns.state.vendingmachinestates.State;

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
