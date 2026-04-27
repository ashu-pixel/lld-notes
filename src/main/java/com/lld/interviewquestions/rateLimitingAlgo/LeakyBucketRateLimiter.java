package com.lld.interviewquestions.rateLimitingAlgo;

import java.util.concurrent.*;

public class LeakyBucketRateLimiter {

    private final int capacity;                 // max queue size
    private final BlockingQueue<Long> queue;    // request queue
    private final ScheduledExecutorService scheduler;

    public LeakyBucketRateLimiter(int capacity, int leakRatePerSecond) {
        this.capacity = capacity;
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        // leak at fixed rate
        long interval = 1000L / leakRatePerSecond;

        scheduler.scheduleAtFixedRate(() -> {
            queue.poll(); // remove one request (leak)
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    /** Try to add request to bucket */
    public boolean allowRequest() {
        // offer is thread-safe
        return queue.offer(System.nanoTime());
    }

    /** Shutdown scheduler */
    public void shutdown() {
        scheduler.shutdown();
    }
}
