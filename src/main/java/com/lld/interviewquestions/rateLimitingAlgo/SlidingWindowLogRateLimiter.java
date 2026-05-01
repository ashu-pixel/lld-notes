package com.lld.interviewquestions.rateLimitingAlgo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowLogRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    private final Deque<Long> log = new ArrayDeque<>();
    private final ReentrantLock lock = new ReentrantLock();

    public SlidingWindowLogRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }

    public boolean allowRequest() {
        long now = System.currentTimeMillis();

        lock.lock();
        try {
            // remove expired timestamps
            while (!log.isEmpty() && now - log.peekFirst() >= windowSizeMillis) {
                log.pollFirst();
            }

            if (log.size() < maxRequests) {
                log.offerLast(now);
                return true;
            }

            return false;

        } finally {
            lock.unlock();
        }
    }
}


/*
Why not user blocking queue?
Ans.
Your algorithm requires three tightly coupled operations:
{
    Remove expired timestamps
    Check current size
    Insert new timestamp
}
These must be atomic as a group so its anyways thread safe using locks 
*/