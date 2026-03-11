package com.MultithreadingQuestions.Easy.OddEvenPrinter.UsingTwoThreads;

public class SharedClass {
    int MAX_LIMIT;
    int currentNumber = 1;

    public SharedClass(int MAX_LIMIT) {
        this.MAX_LIMIT = MAX_LIMIT;
    }

    public void print(){
        System.out.printf("%d printed by %s\n", currentNumber, Thread.currentThread().getName());
        currentNumber++;
    }

    public int getMAX_LIMIT() {
        return MAX_LIMIT;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }
}