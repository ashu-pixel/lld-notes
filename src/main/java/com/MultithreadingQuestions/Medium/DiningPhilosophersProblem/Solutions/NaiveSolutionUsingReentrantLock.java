package com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions;

import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork.Fork;

public class NaiveSolutionUsingReentrantLock extends Philosopher {

    public NaiveSolutionUsingReentrantLock(int id, Fork leftFork, Fork rightFork) {
        super(id, leftFork, rightFork);
    }

    /* Here instead of using synchronized blocks on entire object
    we are using ReentrantLock of the object. */
    @Override
    public void run() {

        try {
            while (true) {
                think();
                if (leftFork.pickUp(id)) {
                    System.out.println("Philosopher " + id + " picked up left fork");
                    if (rightFork.pickUp(id)) {
                        System.out.println("Philosopher " + id + " picked up right fork");
                        eat();
                    }
                    System.out.println("Philosopher " + id + " put down right fork");
                }
                System.out.println("Philosopher " + id + " put down left fork");
            }
        } catch (Exception e) {
            System.out.println("Philosopher " + id + " was interrupted");
        } finally {
            leftFork.release(id);
            rightFork.release(id);
        }
    }
}
