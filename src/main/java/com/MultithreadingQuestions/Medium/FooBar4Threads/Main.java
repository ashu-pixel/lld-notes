package com.MultithreadingQuestions.Medium.FooBar4Threads;


/*
Q. 
Write a program that uses 4 threads to print numbers from 1 to n, replacing:

=> Multiples of 3 with "Fizz"
=> Multiples of 5 with "Buzz"
=> Multiples of both 3 and 5 with "FizzBuzz"
=> Rest should be printed by the number thread."

NOTE: Each thread should be responsible for printing only one of the above.

*/


public class Main {
    public static void main(String[] args) {

        // approach using Synchronized
        //FizzBuzz fizzBuzz = new FizzBuzz(20);

        // approach using Semaphore
        FizzBuzzSemaphore fizzBuzz = new FizzBuzzSemaphore(15);


        Thread thread1 = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread4 = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
