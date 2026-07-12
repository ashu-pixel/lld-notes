package com.MultithreadingQuestions.Medium.DiningPhilosophersProblem;

import java.util.concurrent.Semaphore;

import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork.AdvancedFork;
import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork.Fork;
import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork.SimpleFork;
import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.NaiveSolution;
import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Solution1;
import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Solution2;
import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Solution3;

public class Main {

    public static void main(String[] args) {

        int n = 5;
        Fork[] forks = new Fork[n];
        int solFlag = 1;

        for (int i = 0; i < n; i++) {
            if (solFlag == 4) {
                forks[i] = new AdvancedFork(i);
            } else {
                forks[i] = new SimpleFork();
            }
        }

        /* Solution 1 : Naive  
        This is prone to deadlock as all conditions are met  
         */
        for (int i = 0; i < n && solFlag == 1; i++) {
            new Thread(
                    new NaiveSolution(i, forks[(i + 1) % n], forks[i])
            ).start();
            
            // new Thread(
            //         new NaiveSolutionUsingReentrantLock(i, forks[(i + 1) % n], forks[i])
            // ).start();
        }

        /* Solution 2
        Here we break the circular wait condition by making the last person to pick up out of order 
        -- How ?did not understand 
        There won't be deadlock here but starvation is possible 
         */
        for (int i = 0; i < n && solFlag == 2; i++) {
            new Thread(
                    new Solution1(i, forks[(i + 1) % n], forks[i], i == n - 1)
            ).start();
        }


        /* Solution 3
        Here we break the hold and wait condition by allowing only limited people to get into critical section 
        There won't be deadlock here but starvation is possible 
         */
        Semaphore lock = new Semaphore(n - 1);
        for (int i = 0; i < n && solFlag == 3; i++) {
            new Thread(
                    new Solution2(i, forks[(i + 1) % n], forks[i], lock)
            ).start();
        }


        /* Solution 4
        Ramdom wait added which prevents livelock 
        Hold and release case fixed  
         */
        for (int i = 0; i < n && solFlag == 4; i++) {
            new Thread(
                    new Solution3(i, forks[(i + 1) % n], forks[i])
            ).start();
        }
    }
}
