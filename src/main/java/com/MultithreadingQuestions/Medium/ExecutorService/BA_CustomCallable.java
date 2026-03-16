package com.MultithreadingQuestions.Medium.ExecutorService;

@FunctionalInterface
interface MyCallable {
    int compute() throws Exception;
}

class ReturnRunable implements Runnable {
    private int result;
    MyCallable myCallable;

    public ReturnRunable(MyCallable myCallable) {
        this.myCallable = myCallable;
    }

    @Override
    public void run() {
        try {
            result = myCallable.compute();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public int getResult() {
        return result;
    }

}

public class BA_CustomCallable {

    public static void main(String[] args) throws InterruptedException {
        MyCallable myCallable = () -> {
            Thread.sleep(2000);
            return 42;
        };
        ReturnRunable returnRunable = new ReturnRunable(myCallable);
        Thread thread = new Thread(returnRunable);
        thread.start();
        thread.join();
        System.out.println("Result: " + returnRunable.getResult());
    }

}