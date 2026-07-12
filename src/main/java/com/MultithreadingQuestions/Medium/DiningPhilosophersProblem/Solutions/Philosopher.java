package com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions;

import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork.Fork;

public abstract class Philosopher implements Runnable {

    protected final int id;
    protected final Fork leftFork;
    protected final Fork rightFork;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    protected void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep((long) (Math.random() * 1000));
    }

    protected void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep((long) (Math.random() * 1000));
    }
}
