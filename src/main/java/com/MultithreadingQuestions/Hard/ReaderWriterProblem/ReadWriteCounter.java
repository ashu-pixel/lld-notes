package com.MultithreadingQuestions.Hard.ReaderWriterProblem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteCounter {

    private int count = 0;

    // private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    /* 
+----------------------+------------------------------------------+-------------------------------------------+
| Feature              | Non-Fair Mode (Default)                  | Fair Mode(passing true in constructor)    |
+----------------------+------------------------------------------+-------------------------------------------+
| Ordering             | No ordering guarantee                    | Strict FIFO queue(of incoming threads)    |
| Lock Acquisition     | Threads compete freely                   | Threads granted in arrival order          |
| Starvation           | Possible (reader/writer starvation)      | No starvation                             |
| Throughput           | Higher                                   | Slightly lower                            |
| Latency              | Lower average latency                    | Higher due to queueing                    |
+----------------------+------------------------------------------+-------------------------------------------+
     */

    private final Lock readLock = lock.readLock(); // shared lock
    private final Lock writeLock = lock.writeLock(); // exclusive lock

    public void increment() {
        writeLock.lock();
        try {
            count++;
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        readLock.lock();
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public void lockDowngrading() {

        /* 
        Lock Downgrading:
        Lock downgrading is the process of acquiring a read lock while holding a write lock, 
        and then releasing the write lock, effectively "downgrading" from exclusive access to shared access.
        This is useful when a thread needs to perform some read operations after modifying shared data 
        
        Where as if you try to acquire write lock while holding read lock, 
        it will result in deadlock because reader thread is waiting for write lock to be released, 
        but write lock cannot be acquired until all read locks are released.         
         */
        writeLock.lock();
        try {
            count++;
            readLock.lock();
            /* acquire read lock before releasing write lock becase if you release write lock first, 
            another thread can acquire write lock and modify the count before you acquire read lock
            Resulting this thread not to see its changes  */
        } finally {
            writeLock.unlock(); // release write lock
        }

        /* 
        This is the gap where another thread can acquire write lock and modify the count before you acquire read lock
        To avoid this, you should acquire read lock before releasing write lock,
         */
        try {
            System.out.println("Count after downgrading: " + count);
        } finally {
            readLock.unlock(); // release read lock
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteCounter counter = new ReadWriteCounter();

        Runnable readTask = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " read: " + counter.getCount());
            }
        };

        Runnable writeTask = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
                System.out.println(Thread.currentThread().getName() + " incremented");
            }
        };

        Thread writerThread = new Thread(writeTask);
        Thread readerThread1 = new Thread(readTask);
        Thread readerThread2 = new Thread(readTask);

        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        writerThread.join();
        readerThread1.join();
        readerThread2.join();

        System.out.println("Final count: " + counter.getCount());

        counter.lockDowngrading();

        /* 
        Limitations of ReadWriteLock:
        1. No optimistic reading : 
        - Even if writes are less, readers still aquire-release read lock
        - This introduces overhead of maintaining lock count state which as to be atomically done using CAS operation
        2. Fair mode reduces throughput :
        - Because of strict FIFO, reads which could have been done in parallel are now serialized, reducing throughput

        Java 8 introduced StampedLock which overcomes these limitations 
        */
    }
}
