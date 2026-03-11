package com.lld.behavioralpatterns.state.vendingmachinestates;

import java.util.List;

import com.lld.behavioralpatterns.state.context.Item;
import com.lld.behavioralpatterns.state.context.VendingMachine;


public abstract class State {

    public void clickOnInsertCoinButton(VendingMachine machine) throws Exception {
         // by default nothing happens
    }

    public void clickOnStartProductSelectionButton(VendingMachine machine) throws Exception {
        // by default nothing happens
    }

    public void insertCoin(VendingMachine machine, Coin coin) throws Exception {
        // by default nothing happens
    }

    public void chooseProduct(VendingMachine machine, int codeNumber) throws Exception {
        // by default nothing happens
    }

    public int getChange(int returnChangeMoney) throws Exception {
        // by default nothing happens
        return 0;
    }

    public Item dispenseProduct(VendingMachine machine, int codeNumber) throws Exception {
        // by default nothing happens
        return null;
    }

    public List<Coin> refundFullMoney(VendingMachine machine) throws Exception {
        // by default nothing happens
        return null;
    }

    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception {
        // by default nothing happens
    }
}


