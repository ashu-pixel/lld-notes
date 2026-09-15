package com.lld.interviewquestions.rateLimitingAlgo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeakyBucketRateLimiter implements RateLimiter {

    private final BlockingQueue<Long> queue;    // request queue
    private final ScheduledExecutorService scheduler;

    public LeakyBucketRateLimiter(int capacity, int leakRatePerSecond) {
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        // leak at fixed rate
        long interval = 1000L / leakRatePerSecond;

        scheduler.scheduleAtFixedRate(() -> {
            queue.poll(); // remove one request (leak)
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    /** Try to add request to bucket */
    @Override
    public boolean allowRequest() {
        // offer is thread-safe
        return queue.offer(System.nanoTime());
    }

    /** Shutdown scheduler */
    public void shutdown() {
        scheduler.shutdown();
    }
}
