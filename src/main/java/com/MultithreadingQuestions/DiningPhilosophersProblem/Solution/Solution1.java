package com.MultithreadingQuestions.DiningPhilosophersProblem.Solution;

public class Solution1 extends Philosopher {

    private final boolean isLastPhilosopher ; 

    public Solution1(   int id, Object leftFork, Object rightFork , boolean isLastPhilosopher) {
        super(id, leftFork, rightFork);
        this.isLastPhilosopher = isLastPhilosopher;
    }

    @Override
    public void run() {

        try {
            while (true) {
                think();

                
                if (isLastPhilosopher) {
                    synchronized (rightFork) {
                        System.out.println("Philosopher " + id + " picked up right fork");
                        synchronized (leftFork) {
                            System.out.println("Philosopher " + id + " picked up left fork");
                            eat();
                            System.out.println("Philosopher " + id + " put down left fork");
                        }
                    }
                    System.out.println("Philosopher " + id + " put down right fork");
                } else {
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
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + id + " was interrupted");
        }
    }
}