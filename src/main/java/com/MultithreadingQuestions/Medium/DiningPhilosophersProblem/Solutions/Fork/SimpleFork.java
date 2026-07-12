package com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork;

import java.util.concurrent.locks.ReentrantLock;

public class SimpleFork implements Fork {

    protected final ReentrantLock lock = new ReentrantLock();

    @Override
    public boolean pickUp(int id) throws Exception {
        try {
            lock.lock();
            return true;
        } catch (Exception e) {
            throw new Exception("Fork is not available for Philosopher " + id);
        }
    }

    @Override
    public void release(int id) {
        if (lock.isLocked()) {
            lock.unlock();
        }
    }
}