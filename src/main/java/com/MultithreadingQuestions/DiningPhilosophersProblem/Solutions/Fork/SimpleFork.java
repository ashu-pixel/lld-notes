package com.MultithreadingQuestions.DiningPhilosophersProblem.Solutions.Fork;


public class SimpleFork implements Fork {

    @Override
    public boolean pickUp(int id) throws Exception {
        return true ; 
    }
    
    @Override
    public void release(int id) {
        
    }
    
}
