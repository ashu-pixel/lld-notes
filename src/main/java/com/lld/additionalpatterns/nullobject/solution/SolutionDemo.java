package com.lld.additionalpatterns.nullobject.solution;

import com.lld.additionalpatterns.nullobject.Bike;
import com.lld.additionalpatterns.nullobject.Car;
import com.lld.additionalpatterns.nullobject.Vehicle;

public class SolutionDemo {
    public static void main(String[] args) {
        System.out.println("\n##### Null Object Pattern: Solution Demo #####");

        Vehicle car = VehicleFactory.getVehicle("car");
        printVehicleDetails(car);
        testDrive(car);

        Vehicle bike = VehicleFactory.getVehicle("bike");
        printVehicleDetails(bike);
        testDrive(car);

        // Saved by NULL Check in printVehicleDetails and testDrive methods
        // Without NULL Check, it will not throw NullPointerException or ClassCastException
        Vehicle nullVehicle = VehicleFactory.getVehicle("null");
        printVehicleDetails(nullVehicle);
        testDrive(nullVehicle);
    }

    private static void printVehicleDetails(Vehicle vehicle) {
        if (vehicle instanceof Car car) {
            System.out.print("\n[+] Vehicle Details: ");
            System.out.println(car.getClass().getSimpleName() + " [Model=" + car.getModel()
                    + ", Color=" + car.getColor() + ", Seating Capacity=" + car.getSeatingCapacity()
                    + ", Fuel Tank Capacity=" + car.getFuelTankCapacity() + "]");

        }
        if (vehicle instanceof Bike bike) {
            System.out.print("\n[+] Vehicle Details: ");
            System.out.println(bike.getClass().getSimpleName() + " [Model=" + bike.getModel()
                    + ", Color=" + bike.getColor() + ", Fuel Tank Capacity=" + bike.getFuelTankCapacity() + "]");
        }
    }

    private static void testDrive(Vehicle vehicle) {
        vehicle.start();
        vehicle.stop();
    }
}
