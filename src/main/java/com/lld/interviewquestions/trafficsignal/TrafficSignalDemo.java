package com.lld.interviewquestions.trafficsignal;

public class TrafficSignalDemo {

    public static void main(String[] args) throws InterruptedException {
        TrafficSignal trafficSignal = new TrafficSignal();

        for (int step = 1; step <= 6; step++) {
            System.out.println("\nStep " + step + ":");
            trafficSignal.showSignal();
            trafficSignal.change();
            Thread.sleep(500);
        }
    }
}
