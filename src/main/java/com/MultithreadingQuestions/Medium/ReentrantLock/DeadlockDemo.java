package com.MultithreadingQuestions.Medium.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockDemo {

    /* =========================================================
         PART 1: DEADLOCK USING SYNCHRONIZED
       ========================================================= */

    static class PenSync {
        public synchronized void use(PaperSync paper) {
            System.out.println(Thread.currentThread().getName() + " locked PEN, waiting for PAPER");
            paper.finish(); // needs paper lock
        }

        public synchronized void finish() {
            System.out.println(Thread.currentThread().getName() + " finished using PEN");
        }
    }

    static class PaperSync {
        public synchronized void use(PenSync pen) {
            System.out.println(Thread.currentThread().getName() + " locked PAPER, waiting for PEN");
            pen.finish(); // needs pen lock
        }

        public synchronized void finish() {
            System.out.println(Thread.currentThread().getName() + " finished using PAPER");
        }
    }

    /* =========================================================
         PART 2: DEADLOCK-FREE USING REENTRANT LOCK
       ========================================================= */

    static class Pen {
        ReentrantLock lock = new ReentrantLock();

        public void use(Paper paper) {
            try {
                while (true) {
                    if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " got PEN");

                            if (paper.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " got PAPER");
                                    System.out.println(Thread.currentThread().getName() + " writing safely");
                                    break;
                                } finally {
                                    paper.lock.unlock();
                                }
                            }
                        } finally {
                            lock.unlock();
                        }
                    }
                    Thread.sleep(50); // retry
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Paper {
        ReentrantLock lock = new ReentrantLock();

        public void use(Pen pen) {
            try {
                while (true) {
                    if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " got PAPER");

                            if (pen.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " got PEN");
                                    System.out.println(Thread.currentThread().getName() + " writing safely");
                                    break;
                                } finally {
                                    pen.lock.unlock();
                                }
                            }
                        } finally {
                            lock.unlock();
                        }
                    }
                    Thread.sleep(50); // retry
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {

        /* ================= DEADLOCK DEMO ================= */

        PenSync pen1 = new PenSync();
        PaperSync paper1 = new PaperSync();

        Thread t1 = new Thread(() -> pen1.use(paper1), "T1");
        Thread t2 = new Thread(() -> paper1.use(pen1), "T2");

        t1.start();
        t2.start();

        // Uncomment below to test fixed version instead
        /*
        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread t3 = new Thread(() -> pen.use(paper), "T3");
        Thread t4 = new Thread(() -> paper.use(pen), "T4");

        t3.start();
        t4.start();
        */
    }
}


// DEADLOCK (with synchronized):
// Thread-1 locks PEN → waits for PAPER
// Thread-2 locks PAPER → waits for PEN
// → Circular wait → both threads blocked forever

// Why synchronized fails:
// - No timeout
// - No way to release lock if waiting for another resource

// SOLUTION (ReentrantLock + tryLock):
// - Try acquiring both locks
// - If second lock fails → release first lock → retry
// → breaks circular wait → avoids deadlock

// Key idea:
// Deadlock requires 4 conditions:
// 1. Mutual exclusion
// 2. Hold and wait
// 3. No preemption
// 4. Circular wait
// → We break "circular wait"

// Best practices:
// - Use tryLock() when acquiring multiple locks
// - Always unlock in finally block
// - Consider lock ordering for simpler design