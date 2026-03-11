package com.MultithreadingQuestions.Easy;

import java.util.concurrent.Semaphore;

// Q. Print ABC n times using 3 threads 

/*
Key point to note here- 
With a semaphore, the JVM does not track thread ownership. 
It only tracks available permits. 
So if the same thread calls acquire() again and no permits are available, that thread will block.

This is different from how a reentrant lock (synchronized / ReentrantLock) behaves.
*/

public class ABC {
    public static void main(String[] args) {

        Semaphore aSemaphore = new Semaphore(1);
        Semaphore bSemaphore = new Semaphore(0);
        Semaphore cSemaphore = new Semaphore(0);
        int MAX_LIMIT = 5;

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < MAX_LIMIT; i++) {
                try {
                    aSemaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("A + " + Thread.currentThread().getName());
                bSemaphore.release();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < MAX_LIMIT; i++) {
                try {
                    bSemaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("B + " + Thread.currentThread().getName());
                cSemaphore.release();
            }
        });
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < MAX_LIMIT; i++) {
                try {
                    cSemaphore.acquire();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("C + " + Thread.currentThread().getName());
                aSemaphore.release();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
