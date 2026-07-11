package com.MultithreadingQuestions.DiningPhilosophersProblem.Solution;

import java.util.concurrent.Semaphore;

public class Solution2 extends Philosopher {

    private final Semaphore lock;

    public Solution2(int id, Object leftFork, Object rightFork, Semaphore lock) {
        super(0, leftFork, rightFork);
        this.lock = lock;
    }

    @Override
    public void run() {

        while (true) {
            try {
                think();

                lock.acquire();
                synchronized (leftFork) {
                    System.out.println("Philosopher " + id + " picked up left fork");
                    synchronized (rightFork) {
                        System.out.println("Philosopher " + id + " picked up right fork");
                        eat();
                    }
                    System.out.println("Philosopher " + id + " put down right fork");
                }
                System.out.println("Philosopher " + id + " put down left fork");
            } catch (Exception e) {
                System.out.println("Error");
            } finally {
                lock.release();
            }
        }

    }

}
