package com.MultithreadingQuestions.Easy.OddEvenPrinter.UsingNThreads;

/* 
🧠 Problem 6: Print 1 to N using M threads in sequence
❓ Problem Statement:
You are given a number N and M threads.
Write a multithreaded Java program where each thread prints numbers from 1 to N in a round-robin fashion.

For example, if N = 10 and M = 3, the output should be like:

Thread-1: 1
Thread-2: 2
Thread-3: 3
Thread-1: 4
Thread-2: 5
Thread-3: 6
Thread-1: 7
Thread-2: 8
Thread-3: 9
Thread-1: 10
*/

public class Main {
    public static void main(String[] args) {
        SharedNumberClass sharedNumberClass = new SharedNumberClass(16);
        int totalThreads = 3;
        for (int i = 0; i < totalThreads; i++) {
            Thread thread = new Thread(new Printer(sharedNumberClass, totalThreads, i), "Thread - " + i);
            thread.start();
        }
    }
}