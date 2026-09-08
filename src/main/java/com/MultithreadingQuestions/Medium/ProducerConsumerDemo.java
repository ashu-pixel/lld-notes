package com.MultithreadingQuestions.Medium;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


    /*
     * ===================== PROBLEM =====================
     * 
     * - We have a shared buffer (queue)
     * - Producer adds items
     * - Consumer removes items
     * 
     * Issues:
     * ❌ Race condition (multiple threads accessing queue ie producer is writing and consumer is reading at the same item)
     * ❌ Producer may overfill buffer
     * ❌ Consumer may try to consume empty buffer
     * 
     * Need:
     * ✔ Thread safety
     * ✔ Coordination between threads
     */

class SimpleBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        public SimpleBuffer(int capacity) {
            this.capacity = capacity;
        }

        // PRODUCER
        public synchronized void produce(int value) throws InterruptedException {

            // wait if buffer is full
            while (queue.size() == capacity) {
                System.out.println("Buffer full, producer waiting...");
                wait();
            }

            queue.add(value);
            System.out.println("Produced: " + value);

            // notify consumers
            notifyAll();
        }

        // CONSUMER
        public synchronized int consume() throws InterruptedException {

            // wait if buffer is empty
            while (queue.isEmpty()) {
                System.out.println("Buffer empty, consumer waiting...");
                wait();
            }

            int value = queue.poll();
            System.out.println("Consumed: " + value);

            // notify producers
            notifyAll();

            return value;
        }
    }
class Buffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        private final ReentrantLock lock = new ReentrantLock();

        // Separate condition queues
        private final Condition notFull = lock.newCondition(); // producers wait here
        private final Condition notEmpty = lock.newCondition(); // consumers wait here

        public Buffer(int capacity) {
            this.capacity = capacity;
        }

        // PRODUCER
        public void produce(int value) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == capacity) {
                    System.out.println("Buffer full → producer waiting");
                    notFull.await(); // only producers wait
                }

                queue.add(value);
                System.out.println("Produced: " + value);

                // wake ONE consumer (not all threads)
                notEmpty.signal();

            } finally {
                lock.unlock();
            }
        }

        // CONSUMER
        public int consume() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("Buffer empty → consumer waiting");
                    notEmpty.await(); // only consumers wait
                }

                int value = queue.poll();
                System.out.println("Consumed: " + value);

                // wake ONE producer
                notFull.signal();

                return value;

            } finally {
                lock.unlock();
            }
        }
    }

public class ProducerConsumerDemo {

    public static void main(String[] args) {

        // Problem with this approach is, all the threads wake up.
        // In case where queue is gets some space only producer threads should wake up
        // and
        // In case where queue gets some item only consumer threads should wake up.
        // SimpleBuffer buffer = new SimpleBuffer(5);

        // This is implementation of BlockingQueue.
        Buffer buffer = new Buffer(5);
        /*
        This is implementation of ArrayBlockingQueue as one lock is used for both producer and consumer. 
        So, when producer is producing the item, consumer has to wait for producer to finish and vice versa.

        Unlike in LinkedBlockingQueue where two locks are used for producer and consumer. 
        So, when producer is producing the item, consumer can consume the item at the same time.
        Thus, higher throughput 

        For PriorityBlockingQueue, 
        Never blocks on put 
        take() blocks if queue is empty
        */

        // Producer thread
        Thread producer = new Thread(() -> {
            int value = 1;
            try {
                while (true) {
                    buffer.produce(value++);
                    Thread.sleep(500); // simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    buffer.consume();
                    Thread.sleep(800); // simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }

}