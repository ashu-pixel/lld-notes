package com.lld.interviewquestions.rateLimitingAlgo;

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
