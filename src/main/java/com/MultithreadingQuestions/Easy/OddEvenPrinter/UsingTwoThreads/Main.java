package com.MultithreadingQuestions.Easy.OddEvenPrinter.UsingTwoThreads;

public class Main {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass(25);

        /* Thread thread1 = new Thread(new EvenPrinter(sharedClass), "Even Printer");
        Thread thread2 = new Thread(new OddPrinter(sharedClass), "Odd Printer"); */
        // If you see we are repeating the logic in both the EvenPrinter and OddPrinter class, 
        // we can create a common Printer class and pass the threadId to it, 
        // which will decide whether to print odd or even number.

        Thread thread1 = new Thread(new Printer(sharedClass, 0), "Even Printer");
        Thread thread2 = new Thread(new Printer(sharedClass, 1), "Odd Printer");
        thread1.start();
        thread2.start();
    }
}