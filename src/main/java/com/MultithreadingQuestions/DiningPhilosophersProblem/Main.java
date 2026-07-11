package com.MultithreadingQuestions.DiningPhilosophersProblem;

import java.util.concurrent.Semaphore;

import com.MultithreadingQuestions.DiningPhilosophersProblem.Solution.NaiveSolution;
import com.MultithreadingQuestions.DiningPhilosophersProblem.Solution.Solution1;
import com.MultithreadingQuestions.DiningPhilosophersProblem.Solution.Solution2;

public class Main {

    public static void main(String[] args) {

        int n = 5;
        Object[] forks = new Object[n];
        int solFlag = 3 ; 

        for (int i = 0; i < n; i++) {
            forks[i] = new Object();
        }

        /* Solution 1
        This is prone to deadlock as all conditions are met  
         */
        for (int i = 0; i < n && solFlag == 1; i++) {
            new Thread(new NaiveSolution(i, forks[(i + 1) % n], forks[i])).start();
        }

        /* Solution 2 
        Here we break the circular wait condition by making the last person to pick up out of order 
        -- How ?did not understand 
        There won't be deadlock here but starvation is possible 
         */
        for (int i = 0; i < n && solFlag == 2 ; i++) {
            new Thread(
                    new Solution1(i, forks[(i + 1) % n], forks[i], i == n - 1)
            ).start();
        }


        /* Solution 3 
        Here we break the hold and wait condition by allowing only limited people to get into critical section 
        There won't be deadlock here but starvation is possible 
         */
        Semaphore lock = new Semaphore(n-1) ; 
        for (int i = 0; i < n && solFlag == 3; i++) {
            new Thread(
                    new Solution2(i, forks[(i + 1) % n], forks[i], lock)
            ).start();
        }

    }

}
