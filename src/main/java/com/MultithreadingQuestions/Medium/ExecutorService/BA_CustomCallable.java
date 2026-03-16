package com.MultithreadingQuestions.Medium.ExecutorService;

// This is how its designed in Java

@FunctionalInterface
interface MyCallable<T> {
    T compute() throws Exception;
}

interface MyFuture<T> {
    T getResult() throws InterruptedException;
}

class ReturnRunable<T> implements Runnable, MyFuture<T> {
    private T result;
    MyCallable<T> myCallable;

    public ReturnRunable(MyCallable<T> myCallable) {
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

    @Override
    public T getResult() {
        return result;
    }

}

public class BA_CustomCallable {

    public static void main(String[] args) throws InterruptedException {
        MyCallable<Integer> myCallable = () -> {
            Thread.sleep(2000);
            return 42;
        };
        ReturnRunable<Integer> returnRunable = new ReturnRunable<>(myCallable);
        Thread thread = new Thread(returnRunable);
        thread.start();
        thread.join();
        System.out.println("Result: " + returnRunable.getResult());
    }
}

/*
| Our Class                         | Actual Class in Java                          |
|-----------------------------------|-----------------------------------------------|
| MyCallable<T>                     | Callable<T>                                   |
| - compute()                       | - call()                                      |
|                                   |                                               |
| MyFuture<T>                       | Future<T>                                     |
| - getResult()                     | - get()                                       |
|                                   | - isDone()                                    |
|                                   | - cancel(mayInterrupt)                        |
|                                   | - isCancelled()                               |
|                                   |                                               |
| ReturnRunnable<T>                 | FutureTask<T>                                 |
| implements Runnable               | implements RunnableFuture<T>                  |
| implements MyFuture<T>            | (RunnableFuture extends Runnable + Future<T>) |
*/