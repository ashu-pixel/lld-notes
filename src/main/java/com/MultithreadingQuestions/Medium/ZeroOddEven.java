package com.MultithreadingQuestions.Medium;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* 
Q. Write a program that outputs the series:
 
0 1 0 2 0 3 0 4 0 5...  
zero is called first, then odd and even numbers are called in increasing order.
*/

class ZeroEvenOddUsingReentrantLock {
    private int n;
    private int state = 0;

    private ReentrantLock lock = new ReentrantLock();
    private final Condition condition0 = lock.newCondition();
    private final Condition conditionOdd = lock.newCondition();
    private final Condition conditionEven = lock.newCondition();

    public ZeroEvenOddUsingReentrantLock(int n) {
        this.n = n;
    }

    public void zero() {
        for (int i = 1; i <= n; i++) {
            lock.lock();
            try {
                while (state != 0) {
                    condition0.await();
                }
                System.out.print(0 + " ");
                if (i % 2 == 1) {
                    state = 1;
                    conditionOdd.signal();
                } else {
                    state = 2;
                    conditionEven.signal();
                }
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }
        }
    }

    public void even() {
        for (int i = 2; i <= n; i += 2) {

            lock.lock();
            try {
                while (state != 2) {
                    conditionEven.await();
                }
                System.out.print(i + " ");
                state = 0;
                condition0.signal();
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }

        }
    }

    public void odd() {
        for (int i = 1; i <= n; i += 2) {

            lock.lock();
            try {
                while (state != 1) {
                    conditionOdd.await();
                }
                System.out.print(i + " ");
                state = 0;
                condition0.signal();
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }

        }
    }
}

class ZeroEvenOddUsingSemaphores {
    int n;
    private Semaphore zeroSemaphore = new Semaphore(1);
    private Semaphore evenSemaphore = new Semaphore(0);
    private Semaphore oddSemaphore = new Semaphore(0);
    private int flag = 1;

    public ZeroEvenOddUsingSemaphores(int n) {
        this.n = n;
    }

    public void zero() {

        for (int i = 1; i <= n; i++) {
            try {
                zeroSemaphore.acquire();
                System.out.println(0 + " ");
                if (flag == 1) {
                    oddSemaphore.release();
                } else {
                    evenSemaphore.release();
                }
                flag = 1 - flag;

            } catch (Exception e) {
            }
        }
    }
    
    public void odd() {

        for (int i = 1; i <= n; i+=2) {
            try {
                oddSemaphore.acquire();
                System.out.println(i + " ");
                zeroSemaphore.release();
            } catch (Exception e) {
            }
        }
    }

    public void even() {

        for (int i = 2; i <= n; i+=2) {
            try {
                evenSemaphore.acquire();
                System.out.println(i + " ");
                zeroSemaphore.release();
            } catch (Exception e) {
            }
        }
    }

}

public class ZeroOddEven {

    public static void main(String[] args) throws InterruptedException {

        //ZeroEvenOddUsingReentrantLock zeroEvenOdd = new ZeroEvenOddUsingReentrantLock(5);
        ZeroEvenOddUsingSemaphores zeroEvenOdd = new ZeroEvenOddUsingSemaphores(1000);

        Thread zeroThread = new Thread(zeroEvenOdd::zero);
        Thread evenThread = new Thread(zeroEvenOdd::even);
        Thread oddThread = new Thread(zeroEvenOdd::odd);

        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}