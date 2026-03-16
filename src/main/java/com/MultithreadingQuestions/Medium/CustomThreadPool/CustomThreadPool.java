package com.MultithreadingQuestions.Medium.CustomThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Task implements Runnable {
    private final int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Executing task: " + taskId + " by thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate work
        } catch (InterruptedException e) {
            System.out.println("Task " + taskId + " was interrupted.");
            Thread.currentThread().interrupt(); // Restore the interrupt status
        }
    }
}

class Worker extends Thread {

    BlockingQueue<Task> taskQueue;

    public Worker(BlockingQueue<Task> taskQueue, String name) {
        super(name);
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskQueue.take(); // This will block if the queue is empty
                task.run();
            } catch (InterruptedException e) {
                System.out.println("Worker thread " + Thread.currentThread().getName() + " was interrupted.");
                Thread.currentThread().interrupt(); 
                break;  
            }
        }

    }
}

class ThreadPool {
    private final BlockingQueue<Task> taskQueue;
    private final Worker[] workers;

    public ThreadPool(int numThreads, BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
        this.workers = new Worker[numThreads];
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new Worker(taskQueue, "Worker-" + i);
            workers[i].start();
        }
    }

    public void submit(Task task) throws InterruptedException {
        taskQueue.put(task); // This will block if the queue is full
    }

    public void shutdown() {
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }
}

public class CustomThreadPool {

    public static void main(String[] args) throws InterruptedException {
        //BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(5);
        // Better to use LinkedBlockingQueue as its unbounded by default
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>(5);
        ThreadPool threadPool = new ThreadPool(3, taskQueue);

        for (int i = 0; i < 10; i++) {
            threadPool.submit(new Task(i));
        }

        Thread.sleep(1000); // Let the tasks run for a while
        threadPool.shutdown(); // Interrupt all worker threads to stop them gracefully
    }

}