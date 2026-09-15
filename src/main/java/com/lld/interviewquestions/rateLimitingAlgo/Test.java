package com.lld.interviewquestions.rateLimitingAlgo;

/**
 * ============================================================================
 * RATE LIMITING ALGORITHMS — TRADE-OFF COMPARISON
 * ============================================================================
 *
 * ALGORITHM              | MEMORY  | BURST HANDLING         | ACCURACY  | COMPLEXITY
 * ----------------------------------------------------------------------------
 * Fixed Window Counter   | O(1)    | Allows 2x burst at     | Low       | Very Low
 *                        |         | window edges           |           |
 * ----------------------------------------------------------------------------
 * Sliding Window Log     | O(N)    | Precise, no burst      | Very High | Medium
 *                        | (N=reqs)| leakage                |           |
 * ----------------------------------------------------------------------------
 * Sliding Window Counter | O(1)    | Smooths edge burst     | High      | Medium
 * (weighted avg)         |         | via weighted prev-win  |           |
 * ----------------------------------------------------------------------------
 * Token Bucket           | O(1)    | Allows controlled      | High      | Low-Med
 *                        |         | burst up to bucket cap |           |
 * ----------------------------------------------------------------------------
 * Leaky Bucket           | O(1)    | Smooths output to      | High      | Low-Med
 *                        |         | constant rate, no burst|           |
 * ============================================================================
 *
 * WHEN TO CHOOSE WHAT:
 *
 * - Fixed Window:       Simplest to implement/reason about. Use when rough
 *                        rate limiting is fine and edge-burst (2x) isn't a
 *                        concern (e.g., internal low-stakes APIs).
 *
 * - Sliding Log:         Use when you need exact precision (billing-grade,
 *                        strict SLAs) and can afford O(N) memory per user
 *                        (low request volume per key).
 *
 * - Sliding Window       Best general-purpose default: near-accurate like
 *   Counter:              sliding log but O(1) memory. Used by Cloudflare,
 *                        Kong in production. Good interview "safe pick".
 *
 * - Token Bucket:        Use when you WANT to allow bursts intentionally
 *                        (e.g., user can burst 100 reqs then throttle to
 *                        10/sec). Common for public APIs (AWS, Stripe).
 *                        Also easy to implement distributed via Redis
 *                        (INCR + TTL / Lua script).
 *
 * - Leaky Bucket:        Use when you need a CONSTANT outgoing rate
 *                        regardless of input burstiness — e.g., protecting
 *                        a downstream service/queue that can't handle
 *                        spikes at all (traffic shaping).
 * ============================================================================
 */

public class Test {
    RateLimiter rateLimiter = new FixedWindowCounterRateLimiter(1, 1000);

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        for (int i = 0; i < 10; i++) {
            if (test.rateLimiter.allowRequest())
                System.out.println("Request " + i + " allowed");
            else
                System.out.println("Request " + i + " denied");

            Thread.sleep(200);
        }
    }
}
