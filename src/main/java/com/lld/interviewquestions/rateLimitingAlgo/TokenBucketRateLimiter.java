package com.lld.interviewquestions.rateLimitingAlgo;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class TokenBucketRateLimiter {

    private final long capacity;          // max tokens
    private final double refillRate;      // tokens per second

    private double tokens;                // can be fractional
    private long lastRefillTimeNanos;     // monotonic time

    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucketRateLimiter(long capacity, double refillRatePerSecond) {
        if (capacity <= 0 || refillRatePerSecond <= 0) {
            throw new IllegalArgumentException("capacity and refillRate must be > 0");
        }
        this.capacity = capacity;
        this.refillRate = refillRatePerSecond;
        this.tokens = capacity; // start full
        this.lastRefillTimeNanos = System.nanoTime();
    }

    /** Try to acquire 1 token. Returns immediately (non-blocking). */
    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    /** Try to acquire n tokens. Returns immediately (non-blocking). */
    public boolean tryAcquire(int permits) {
        if (permits <= 0 || permits > capacity) throw new IllegalArgumentException("permits must be > 0 and <= capacity");

        lock.lock();
        try {
            refill();

            if (tokens >= permits) {
                tokens -= permits;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /** Refill tokens based on elapsed time. */
    private void refill() {
        long now = System.nanoTime();
        long elapsedNanos = now - lastRefillTimeNanos;
        if (elapsedNanos <= 0) return;

        double tokensToAdd = (elapsedNanos / 1_000_000_000.0) * refillRate;
        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTimeNanos = now;
        }
    }

    // --- Optional helpers ---

    /** Current available tokens (approx, since time moves). */
    public double getAvailableTokens() {
        lock.lock();
        try {
            refill();
            return tokens;
        } finally {
            lock.unlock();
        }
    }

    /** Try acquire with a small wait (spins with sleep) */
    public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        while (System.nanoTime() < deadline) {
            if (tryAcquire(permits)) return true;
            // backoff to reduce contention
            Thread.sleep(1);
        }
        return false;
    }
}