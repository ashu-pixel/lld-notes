package com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AdvancedFork implements  Fork {

    protected final ReentrantLock lock = new ReentrantLock();
    protected final int forkId;

    public AdvancedFork(int forkId) {
        this.forkId = forkId;
    }

    @Override
    public boolean pickUp(int id) throws Exception {
        
        boolean aquired = lock.tryLock(100, TimeUnit.MILLISECONDS);
        
        if (aquired) {
            System.out.println("Aquired fork by: " + id);
        }
        return aquired;
    }
    
    @Override
    public void release(int id) {
        lock.unlock();
        System.out.println("Released fork by: " + id);
    }
}