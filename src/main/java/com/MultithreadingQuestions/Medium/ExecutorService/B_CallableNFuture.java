package com.MultithreadingQuestions.Medium.ExecutorService;

public class B_CallableNFuture {
    public static void main(String[] args) {

        // Limitations of Runnable
        // 1. Cannot return a result
        // 2. Cannot throw checked exceptions for caller to handle

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Checked exception thrown from thread");
            }
        });

        try {
            thread.start();
        } catch (Exception e) {
            // cannot catch exception thrown from thread
            System.out.println("Exception caught");
        }

        // To overcome limitation of not returning result we can use our custom class BA_CustomCallable 
        // Clean way to overcome these limitations is to use Callable and Future 

    }

}
