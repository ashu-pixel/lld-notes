package com.MultithreadingQuestions.Easy.OddEvenPrinter.UsingTwoThreads;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass(25);

        /* Approach 1 */
        /*
         * Thread thread1 = new Thread(new EvenPrinter(sharedClass), "Even Printer");
         * Thread thread2 = new Thread(new OddPrinter(sharedClass), "Odd Printer");
         */
        // If you see we are repeating the logic in both the EvenPrinter and OddPrinter
        // class,
        // we can create a common Printer class and pass the threadId to it,
        // which will decide whether to print odd or even number.

        /* Approach 2 */
        /*
         * Thread thread1 = new Thread(new Printer(sharedClass, 0), "Even Printer");
         * Thread thread2 = new Thread(new Printer(sharedClass, 1), "Odd Printer");
         * thread1.start();
         * thread2.start();
         */

        /* Approach 3 */
        Semaphore odd = new Semaphore(1);
        Semaphore even = new Semaphore(0);
        Thread t1 = new Thread(() -> {
            for (; sharedClass.getCurrentNumber() <= sharedClass.getMAX_LIMIT();) {
                try {
                    odd.acquire();
                    Thread.sleep(1000);
                    if (sharedClass.getCurrentNumber() <= sharedClass.getMAX_LIMIT())
                        sharedClass.print();

                    even.release();
                } catch (Exception e) {
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (; sharedClass.getCurrentNumber() <= sharedClass.getMAX_LIMIT();) {
                try {
                    even.acquire();
                    Thread.sleep(1000);
                    if (sharedClass.getCurrentNumber() <= sharedClass.getMAX_LIMIT())
                        sharedClass.print();
                    odd.release();
                } catch (Exception e) {
                }
            }
        });

        t1.start();
        t2.start();
    }
}