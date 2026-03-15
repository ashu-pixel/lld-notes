package com.MultithreadingQuestions.Medium.CustomThreadPool;

import java.util.concurrent.locks.ReentrantLock;

/* 
Thread.Interupt() is a polite way to ask thread to stop.
Meaning it just asks the thread does not force the thread to stop 

Interupt is only thrown in case when thread is waiting in sleep, wait, join, blockingQ.take 
But the flag might get set its your job to handle if its set 

*/

class MyThread extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Running thread: " + Thread.currentThread().getName());
                System.out.println("Is thread interrupted BEFORE: " + Thread.currentThread().isInterrupted());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted during sleep: " + Thread.currentThread().getName());

                // After catching JVM internally sets the interrupt flag to false
                System.out.println("Is thread interrupted AFTER: " + Thread.currentThread().isInterrupted());

                // Therefore we need to set the interrupt flag again for the upper layers(in
                // huge programs) to know that the thread was interrupted
                Thread.currentThread().interrupt();

                break;
            }
        }
    }
}

// Case when thread is waiting for rehentrant lock then
// interrupt will not throw InterruptedException but it will set the interrupt
// flag to true
// when the lock is released and it sees the sleep method it will throw
// InterruptedException and the thread will be interrupted
// Note : There is not way to interrupt a thread when its waiting for synchronized block
class MyThread2 {
    private final ReentrantLock lock = new ReentrantLock();

    public void doWork() {
        try {
            // This case is explained in the main method
            // lock.lock();

            // this throws InterruptedException immediately 
            lock.lockInterruptibly();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted during sleep: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt(); // Restore the interrupt status
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            System.out.println("Thread was interrupted during lock wait: " + Thread.currentThread().getName());

        }
    }
}

public class Interupts {

    public static void main(String[] args) throws InterruptedException {
        // Case 1
        // MyThread t1 = new MyThread();
        // t1.start();
        // t1.interrupt();

        // Case 2
        MyThread2 worker = new MyThread2();
        Thread t1 = new Thread(worker::doWork, "Thread-1");
        Thread t2 = new Thread(worker::doWork, "Thread-2");
        t1.start(); // Thread-1 acquires the lock and goes to sleep
        t2.start(); // Thread-2 tries to acquire the lock and waits
        t2.interrupt(); // Interrupt Thread-2 while it's waiting for the lock. It won't throw
                        // InterruptedException immediately but will set the interrupt flag to true.
                        // Once Thread-1 releases the lock and Thread-2 acquires it, it will check the
                        // interrupt flag and throw InterruptedException during sleep.
        
    }
}


 
// | Case                                | Can be interrupted immediately?  | Interrupt Flag Set? | Flag Cleared Automatically? | Why? |
// |-------------------------------------|----------------------------------|---------------------|-----------------------------|------|
// | Thread.sleep()                      | Yes                              | Yes                 | Yes                         | sleep() throws InterruptedException and clears the flag when the exception is thrown. |
// | BlockingQueue.take()                | Yes                              | Yes                 | Yes                         | BlockingQueue methods are interruptible and throw InterruptedException, clearing the flag. |
// | ReentrantLock.lockInterruptibly()   | Yes                              | Yes                 | Yes                         | Designed to respond to interrupts and throws InterruptedException. |
// | ReentrantLock.lock()                | No                               | Yes                 | No                          | lock() ignores interrupts while waiting for the lock; flag remains set. |
// | synchronized                        | No                               | Yes                 | No                          | JVM monitor lock acquisition cannot be interrupted; thread waits until lock is available. |
// | Normal running thread (not blocked) | No (keeps running)               | Yes                 | No                          | interrupt() only sets the flag; the thread must check it manually. |



 