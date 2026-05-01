package com.lld.interviewquestions.rateLimitingAlgo;

import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowCounterRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    private long windowStart;     // start of current window
    private int currentCount;
    private int previousCount;

    private final ReentrantLock lock = new ReentrantLock();

    public SlidingWindowCounterRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.windowStart = System.currentTimeMillis();
        this.currentCount = 0;
        this.previousCount = 0;
    }

    public boolean allowRequest() {
        long now = System.currentTimeMillis();

        lock.lock();
        try {
            long elapsed = now - windowStart;

            // shift window if needed
            if (elapsed >= windowSizeMillis) {
                // move current -> previous
                previousCount = currentCount;

                // reset current
                currentCount = 0;

                // align new window
                windowStart = now;
                elapsed = 0;
            }

            // weight of previous window contribution
            double overlapRatio =
                    (double)(windowSizeMillis - elapsed) / windowSizeMillis;

            double effectiveCount =
                    currentCount + (previousCount * overlapRatio);

            if (effectiveCount < maxRequests) {
                currentCount++;
                return true;
            }

            return false;

        } finally {
            lock.unlock();
        }
    }
}