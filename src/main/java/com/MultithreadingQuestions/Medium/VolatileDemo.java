package com.MultithreadingQuestions.Medium;

public class VolatileDemo {

    /*
    ===================== PROBLEM =====================

    One thread updates a flag (running = false),
    but another thread may NEVER see the update
    due to CPU caching → infinite loop.

    This is a VISIBILITY problem.
    */

    static class WithoutVolatile {
        static boolean running = true;

        public static void main(String[] args) throws Exception {

            Thread worker = new Thread(() -> {
                System.out.println("Worker started");

                while (running) {
                    // may never stop due to cached value
                }

                System.out.println("Worker stopped");
            });

            worker.start();

            Thread.sleep(1000);
            running = false; // update may not be visible

            System.out.println("Main updated running = false");
        }
    }


    /*
    ===================== SOLUTION =====================

    Use 'volatile' to ensure visibility across threads.
    */

    static class WithVolatile {
        static volatile boolean running = true;

        public static void main(String[] args) throws Exception {

            Thread worker = new Thread(() -> {
                System.out.println("Worker started");

                while (running) {
                    // always reads latest value
                }

                System.out.println("Worker stopped");
            });

            worker.start();

            Thread.sleep(1000);
            running = false;

            System.out.println("Main updated running = false");
        }
    }


    /*
    ===================== EXPLANATION =====================

    - volatile ensures VISIBILITY:
      → changes by one thread are immediately visible to others

    - prevents CPU caching issues and instruction reordering

    - DOES NOT guarantee atomicity

    - Use volatile for:
      ✔ flags (isRunning, shutdown)
      ✔ status indicators

    - DO NOT use volatile for:
      ❌ count++
      ❌ compound operations
    */
}