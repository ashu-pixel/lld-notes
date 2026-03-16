package com.MultithreadingQuestions.Medium.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class A_TypesOfThreadPool {
    public static void main(String[] args) {

        /*
         * when the task is CPU intensive, like compunation, then we we keep number of
         * threads ~ number of cores, but
         * in I/O bounded task, its adviced to keep threads more than number of cores,
         * because in I/O bounded task, threads will be waiting for I/O operations to
         * complete,
         * so we can have more threads to keep the CPU busy
         * threads = cores * (1 + wait_time / service_time)
         */

        ExecutorService executorService;

        // TYPE1-------------------------------------------------------------------------
        // Fixed thread pool with 3 threads
        executorService = Executors.newFixedThreadPool(3);
        // ------------------------------------------------------------------------------

        // TYPE2-------------------------------------------------------------------------
        // Cached thread pool - creates new threads as needed, but will reuse previously
        // constructed threads when they are available
        // If some threads are idle for 60 seconds, they will be killed and removed
        executorService = Executors.newCachedThreadPool();
        // ------------------------------------------------------------------------------

        // TYPE3-------------------------------------------------------------------------
        // Single thread executor - tasks are stored sequentially executed by single
        // thread
        // If the thread is abrubtly terminated, the executor will create a new one and
        // start executing from where the previous left -- only reason why this exists
        executorService = Executors.newSingleThreadExecutor();
        // ------------------------------------------------------------------------------

        for (int i = 0; i < 10; i++) {

            executorService.submit(() -> {
                while (true) {
                    System.out.println("Task running on " + Thread.currentThread().getName());
                    Thread.sleep(2000);
                }
            });
        }

        // TYPE4-------------------------------------------------------------------------
        // Scheduled thread pool - schedule task to run after delay or reun periodically
        ScheduledExecutorService schExecutorService = Executors.newScheduledThreadPool(3);
        // ------------------------------------------------------------------------------

        // run once after 5 seconds delay
        schExecutorService.schedule(() -> {
            System.out.println("Delayed task ran on " + Thread.currentThread().getName());
        }, 5, TimeUnit.SECONDS);

        // delay at start of task
        schExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("Scheduled task running on " + Thread.currentThread().getName());
        }, 0, 5, TimeUnit.SECONDS);

        // delay at end of task
        schExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("Scheduled task with fixed delay running on " + Thread.currentThread().getName());
        }, 0, 5, TimeUnit.SECONDS);

        // TYPE5-------------------------------------------------------------------------
        // work stealing pool - creates a thread pool that maintains enough threads to
        // support the given parallelism level
        // Suppose a thread is idle, it can "steal" tasks from other threads to keep
        // itself busy, which can improve performance in some cases
        // Number of threads in the pool is equal to the number of available
        // processors(Runtime.getRuntime().availableProcessors())
        executorService = Executors.newWorkStealingPool();
        // ------------------------------------------------------------------------------

    }
}

// internal parameters of thread pool

/*
core pool size: the number of threads to keep in the pool, even if they are idle won't be killed
max pool size: the maximum number of threads to allow in the pool
| Pool Type              | Core Pool Size     | Max Pool Size        | Keep Alive Time |
|------------------------|--------------------|----------------------|-----------------|
| Fixed Thread Pool      | nThreads (User)    | nThreads (User)      | 0 (Ignored)     |
| Cached Thread Pool     | 0                  | Integer.MAX_VALUE    | 60 Seconds      |
| Single Thread Executor | 1                  | 1                    | 0 (Ignored)     |
| Scheduled Pool         | coreSize (User)    | Integer.MAX_VALUE    | 10 Milliseconds |
*/


/*
 * TYPES OF QUEUE IN THREAD POOL
 * -------------------------------------------------------
 * Whether to use 1. LinkedBlockingQueue or 2. ArrayBlockingQueue ??
 * 
 * - If threads are fixed to we want the tasks to pile up in the queue, then we
 * can use LinkedBlockingQueue as its unbounded by default, but if we want to
 * limit the number of tasks in the queue, then we can use ArrayBlockingQueue as
 * it is bounded and we can specify the capacity of the queue, if the queue is
 * full, then the submit method will block until there is space in the queue
 * 
 * - If threads are not fixed, then we can use ArrayBlockingQueue as it is
 * bounded
 * by default, and the thread pool will create new threads as needed to execute
 * the tasks, theoretially, makes the queue never full
 * 
 * 
 * 3. SynchronousQueue is a special type of queue that does not have any
 * capacity,
 * it will only take a task if there is a thread available(avaiable or new can
 * be created) to execute it, otherwise block
 * 
 * 
 * 4. DelayedWorkQueue is a special type of queue that holds tasks until a
 * specified delay has expired, it is used in ScheduledThreadPoolExecutor
 */

/*
 * Once the queue is full and all threads are also busy
 * Rejection policies
 * 
 * 1. AbortPolicy - throws RejectedExecutionException
 * 
 * 2. CallerRunsPolicy - the task will be run in the caller thread, which is the
 * thread that called the submit method, this can help to reduce the load on the
 * thread pool and prevent it from being overwhelmed, by creating
 * backpressure(how ?)
 * Application itself will get slower in putting the tasks into queue as those
 * threads are busy executing thus reducing chances of app crash
 * 
 * 
 * 3. DiscardPolicy - the task will be silently discarded without any
 * notifcation
 * 
 * 4. DiscardOldestPolicy - the oldest unhandled task in the queue will be
 * discarded and then the new task will be added to the queue
 * 
 */

// ForkJoinPool is a special type of thread pool that is designed for work
// stealing, it is used in parallel streams and it is based on the Fork/Join
// framework, it is designed to efficiently execute tasks that can be broken
// down into smaller subtasks, it is not suitable for tasks that are not CPU
// intensive, as it can lead to thread starvation and poor performance