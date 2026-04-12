package com.MultithreadingQuestions.Medium.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
Note - 
one of the characteristics of ReentrantLock, that differentiates it from Semaphore, 
is that after a lock is aquired on ReentrantLock, it can only be released by the thread that holds the lock. 
In case of Semaphore, a permit can be released from any thread. */

class PingPong {
    private int n;
    private ReentrantLock lock = new ReentrantLock();
    private final Condition pingCondition = lock.newCondition();
    private final Condition pongCondition = lock.newCondition();
    private boolean isPingTurn = true;

    public PingPong(int n) {
        this.n = n;
    }

    public void ping() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (!isPingTurn) {
                    pingCondition.await();
                }
                System.out.println("ping ");
                isPingTurn = false;
                pongCondition.signal();
            } finally {
                /* This is always required if not done program will be stuck forever */
                lock.unlock();
            }
        }
    }

    public void pong() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (isPingTurn) {
                    pongCondition.await();
                }
                System.out.println("pong ");
                isPingTurn = true;
                pingCondition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

public class PingPongUsingReentrantLock {

    public static void main(String[] args) {

        PingPong pingPong = new PingPong(5);

        Thread pingThread = new Thread(() -> {
            try {
                pingPong.ping();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread pongThread = new Thread(() -> {
            try {
                pingPong.pong();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pingThread.start();
        pongThread.start();
    }

}