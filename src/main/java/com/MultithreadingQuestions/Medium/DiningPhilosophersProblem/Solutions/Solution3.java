package com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions;

import com.MultithreadingQuestions.Medium.DiningPhilosophersProblem.Solutions.Fork.Fork;

public class Solution3 extends Philosopher {

    public Solution3(int id, Fork leftFork, Fork rightFork) {
        super(id, leftFork, rightFork);
    }

    @Override
    public void run() {

        while (true) {

            try {
                think();

                /* we loop until ate. Don't want to think until ate */
                boolean ate = false ; 
                while(!ate){

                    boolean leftAquired = leftFork.pickUp(id) ; 

                    if(leftAquired){

                        System.out.println("Left lock aquired"); 

                        boolean rightAquired = rightFork.pickUp(id) ; 

                        if(rightAquired){
                            System.out.println("Right lock aquired");
                            eat();
                            ate = true ; 
                            rightFork.release(id);
                            leftFork.release(id);
                        }
                        else{
                            System.out.println("Could not aquire right lock. Left released");
                            leftFork.release(id);/* Breaking hold and wait condition */

                            /* Random sleep to avoid livelock */
                            Thread.sleep((long)(Math.random() * 1000));
                        }
                    }else {
                        System.out.println("Could not aquire left lock. Going to sleep for random time");
                        Thread.sleep((long)(Math.random() * 1000));
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
