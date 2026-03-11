package com.lld.behavioralpatterns.state.context;

import java.util.ArrayList;
import java.util.List;

import com.lld.behavioralpatterns.state.vendingmachinestates.Coin;
import com.lld.behavioralpatterns.state.vendingmachinestates.State;
import com.lld.behavioralpatterns.state.vendingmachinestates.impl.IdleState;

    public class VendingMachine {

        private State vendingMachineState;
        private Inventory inventory;
        private List<Coin> coinList;

        public VendingMachine(){
            vendingMachineState = new IdleState();
            inventory = new Inventory(10);
            coinList = new ArrayList<>();
        }

        public State getVendingMachineState() {
            return vendingMachineState;
        }

        public void setVendingMachineState(State vendingMachineState) {
            this.vendingMachineState = vendingMachineState;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public void setInventory(Inventory inventory) {
            this.inventory = inventory;
        }

        public List<Coin> getCoinList() {
            return coinList;
        }

        public void setCoinList(List<Coin> coinList) {
            this.coinList = coinList;
        }
    }


