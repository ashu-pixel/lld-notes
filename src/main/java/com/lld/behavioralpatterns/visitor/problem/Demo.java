package com.lld.behavioralpatterns.visitor.problem;

// Usage
public class Demo {
    public static void main(String[] args) {
        System.out.println("##### Visitor Pattern: Problem Demo #####");
        SuiteHotelRoom suite = new SuiteHotelRoom("301", "2");
        suite.clean();
        suite.deliverRoomService("Breakfast");
        suite.calculatePrice();
    }
}
