package com.MultithreadingQuestions.Easy;

// Q. Write a program to print "Ping" and "Pong" alternately using two threads. 
// The first thread should print "Ping" and the second thread should print "Pong".

import java.util.concurrent.Semaphore;

public class PingPong {
    public static void main(String[] args) {

        Semaphore pingSemaphore = new Semaphore(1);

        // note that even on 0 locks we can aquire the lock
        // when we release the lock it will increase the number of locks by 1(newly
        // added lock)
        Semaphore pongSemaphore = new Semaphore(0);
        int MAX_LIMIT = 5;

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < MAX_LIMIT; i++) {
                try {
                    pingSemaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Ping + " + Thread.currentThread().getName());
                pongSemaphore.release();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < MAX_LIMIT; i++) {
                try {
                    pongSemaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Pong + " + Thread.currentThread().getName());
                pingSemaphore.release();
            }
        });
        thread1.start();
        thread2.start();
    }
}