package com.lld.interviewquestions.rateLimitingAlgo;

import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowCounterRateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    private long windowStart;
    private int requestCount;

    private final ReentrantLock lock = new ReentrantLock();

    public FixedWindowCounterRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.windowStart = System.currentTimeMillis();
        this.requestCount = 0;
    }

    public boolean allowRequest() {
        long now = System.currentTimeMillis();

        lock.lock();
        try {
            // Move to next window if needed
            if (now - windowStart >= windowSizeMillis) {
                windowStart = now;
                requestCount = 0;
            }

            if (requestCount < maxRequests) {
                requestCount++;
                return true;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }
}