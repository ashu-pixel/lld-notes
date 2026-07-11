package com.MultithreadingQuestions.DiningPhilosophersProblem.Solution;

public class NaiveSolution extends Philosopher {

    
    public NaiveSolution(int id, Object leftFork, Object rightFork) {
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
