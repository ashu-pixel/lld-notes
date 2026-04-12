package com.MultithreadingQuestions.Medium;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    /*
    ===================== PROBLEM =====================

    Multiple threads increment a shared counter.

    count++ is NOT atomic:
    - read
    - modify
    - write

    → leads to race condition and wrong result
    */

    static class WithoutAtomic {
        static int count = 0;

        public static void main(String[] args) throws Exception {

            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    count++; // NOT thread-safe
                }
            };

            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("Final count: " + count); // usually < 2000
        }
    }


    /*
    ===================== SOLUTION =====================

    Use AtomicInteger for atomic operations.
    */

    static class WithAtomic {
        static AtomicInteger count = new AtomicInteger(0);

        public static void main(String[] args) throws Exception {

            Runnable task = () -> {
                for (int i = 0; i < 1000; i++) {
                    count.incrementAndGet(); // atomic
                }
            };

            Thread t1 = new Thread(task);
            Thread t2 = new Thread(task);

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("Final count: " + count.get()); // always 2000
        }
    }


    /*
    ===================== EXPLANATION =====================

    - AtomicInteger provides ATOMICITY + VISIBILITY

    - Uses CAS (Compare-And-Swap):
      → hardware-level atomic instruction
      → no locks required (lock-free)

    - Guarantees:
      ✔ thread-safe updates
      ✔ no race condition

    - Use Atomic when:
      ✔ counters
      ✔ shared mutable state
      ✔ concurrent updates

    - Better than synchronized for low contention

    - volatile keyword ensures that Threads read from memory does not guarantee the synchronization of threads  

    -use volatile only when the operation is atomic 
     eg counter++ is not atomic 
     its read -> increment -> save 
     so even when you mark that as volatile it still might not give correct value.
     Use atomic int in that case
    */
}
