package com.MultithreadingQuestions.DiningPhilosophersProblem.Solutions;

import com.MultithreadingQuestions.DiningPhilosophersProblem.Solutions.Fork.Fork;

public class NaiveSolution extends Philosopher {

    
    public NaiveSolution(int id, Fork leftFork, Fork rightFork) {
        super(id, leftFork, rightFork);
    }

    @Override
    public void run() {

        try {
            while (true) {
                think();
                synchronized (leftFork) {
                    System.out.println("Philosopher " + id + " picked up left fork");
                    synchronized (rightFork) {
                        System.out.println("Philosopher " + id + " picked up right fork");
                        eat();
                    }
                    System.out.println("Philosopher " + id + " put down right fork");
                }
                System.out.println("Philosopher " + id + " put down left fork");
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + id + " was interrupted");
        }
    }
}
