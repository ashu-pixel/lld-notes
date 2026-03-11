package com.MultithreadingQuestions.Easy.OddEvenPrinter.UsingTwoThreads;

public class OddPrinter implements Runnable {

    public final SharedClass sharedClass;

    public OddPrinter(SharedClass sharedClass) {
        this.sharedClass = sharedClass;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (sharedClass) {
                while (sharedClass.getCurrentNumber() <= sharedClass.getMAX_LIMIT()
                        && sharedClass.getCurrentNumber() % 2 == 0) {
                    try {
                        sharedClass.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (sharedClass.currentNumber > sharedClass.getMAX_LIMIT()) {
                    sharedClass.notifyAll();
                    break;
                }
                sharedClass.print();
                sharedClass.notifyAll();
            }
        }
    }
}
