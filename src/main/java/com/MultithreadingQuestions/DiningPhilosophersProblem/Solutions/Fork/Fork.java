package com.MultithreadingQuestions.DiningPhilosophersProblem.Solutions.Fork;

public interface Fork {
    boolean pickUp(int id) throws Exception; 
    void release(int id)  ; 
}
