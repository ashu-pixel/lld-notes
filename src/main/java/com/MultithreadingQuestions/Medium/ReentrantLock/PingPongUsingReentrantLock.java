package com.MultithreadingQuestions.Medium.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
Note - 
Characteristics of ReentrantLock, that differentiates it from Semaphore, 

After a lock is aquired on ReentrantLock, it can only be released by the thread that holds the lock. 
And same thread can aquire the lock again giving re-entry capability 
Only when count(thread aquiring lock) = count(same thread releasing locks) lock is unlocked

In case of Semaphore, a permit can be released from any thread. 
Does not provide re-entry capability 
 */
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