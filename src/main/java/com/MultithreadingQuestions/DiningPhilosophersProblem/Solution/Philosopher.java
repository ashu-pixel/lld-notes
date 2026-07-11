package com.MultithreadingQuestions.DiningPhilosophersProblem.Solution;

public abstract class Philosopher implements Runnable {

    protected final int id;
    protected final Object leftFork;
    protected final Object rightFork;

    public Philosopher(int id, Object leftFork, Object rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    protected void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking");
        Thread.sleep(1000);
    }

    protected void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating");
        Thread.sleep(1000);
    }
}