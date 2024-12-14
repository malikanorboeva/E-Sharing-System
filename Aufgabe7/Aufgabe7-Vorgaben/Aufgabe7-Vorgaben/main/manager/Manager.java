package main.provider.main.manager;

import Prog1Tools.IOTools;
import main.provider.main.IProvider;
import main.provider.main.IShareable;
import main.provider.main.provider.*;
import main.provider.main.user.*;
import main.provider.main.vehicles.*;

public class Manager {
    public static void main(String[] args) {
        // Create providers
        System.out.println("Creating providers...");
        IProvider bikesProvider = new Bikes();
        IProvider mobileProvider = new Mobile();
        IProvider shareProvider = new Share();
        System.out.println("Created: " + bikesProvider.getName() + ", " + mobileProvider.getName() + ", " + shareProvider.getName());

        // Create vehicles with different types
        System.out.println("\nCreating vehicles...");
        Vehicle[] vehicles = createVehicles(bikesProvider, mobileProvider, shareProvider);
        printVehicles(vehicles);

        // Create users with different tariffs and licenses
        System.out.println("\nCreating users...");
        User[] users = createUsers();
        printUsers(users);

        runAllScenarios(users, vehicles, mobileProvider);
    }

    private static void runAllScenarios(User[] users, Vehicle[] vehicles, IProvider mobileProvider) {
        System.out.println("\nStarting test scenarios...");

        // Scenario 1
        System.out.println("\n=== Scenario 1: Standard user rents a scooter ===");
        System.out.println("Press Enter to start this scenario...");
        IOTools.readLine();

        testScooterRental(users[0], (Scooter)vehicles[0]);

        // Scenario 2
        System.out.println("\n=== Scenario 2: Prepaid user rental test ===");
        System.out.println("Press Enter to start this scenario...");
        IOTools.readLine();

        testPrepaidUserRental(users[1], (TinyScooter)vehicles[2]);

        // Scenario 3
        System.out.println("\n=== Scenario 3: License restriction test ===");
        System.out.println("Press Enter to start this scenario...");
        IOTools.readLine();

        testLicenseRestriction(users[3], (Scooter)vehicles[1]);

        // Scenario 4
        System.out.println("\n=== Scenario 4: Car rental test ===");
        System.out.println("Press Enter to start this scenario...");
        IOTools.readLine();

        testCarRental(users[0], new Car("B-Test-1", "Car", mobileProvider.getName(), 100, false, false));

        // Scenario 5
        System.out.println("\n=== Scenario 5: Bike rental test ===");
        System.out.println("Press Enter to start this scenario...");
        IOTools.readLine();

        testBikeRental(users[2], (Bike)vehicles[3]);

        System.out.println("\nAll scenarios completed. Press Enter to exit...");
        IOTools.readLine();
    }

    private static Vehicle[] createVehicles(IProvider bikes, IProvider mobile, IProvider share) {
        return new Vehicle[] {
                new Scooter("B-S-1", "Scooter", bikes.getName(), 100, false, false),
                new Scooter("B-S-2", "Scooter", mobile.getName(), 100, false, false),
                new TinyScooter("B-TS-1", "TinyScooter", share.getName(), 100, TinyScooter.LED.GRÜN),
                new Bike("B-B-1", "Bike", bikes.getName(), 100, false)
        };
    }

    private static User[] createUsers() {
        return new User[] {
                new User("standardUser", 1000, User.Tariff.STANDARD, User.License.SCOOTER_CAR, null),
                new User("prepaidUser", 0, User.Tariff.PREPAID, null, null),
                new User("bikeUser", 500, User.Tariff.STANDARD, null, null),
                new User("carOnlyUser", 1000, User.Tariff.STANDARD, User.License.CAR, null)
        };
    }

    private static void printVehicles(Vehicle[] vehicles) {
        for (Vehicle v : vehicles) {
            System.out.println(v.getText());
        }
    }

    private static void printUsers(User[] users) {
        for (User u : users) {
            u.getText();
        }
    }

    private static void testScooterRental(User user, Scooter scooter) {
        System.out.println("Attempting to reserve scooter for " + user.getUsername());
        if (scooter.reserve(user)) {
            scooter.setSideLight(true);
            user.setVehiclesRented(scooter.getText());
            System.out.println("Reservation successful");

            System.out.println("Starting rental...");
            if (scooter.start(user, scooter)) {
                scooter.setFrontRearLight(true);
                simulateRental(user, scooter);
            }
        } else {
            System.out.println("Reservation failed");
        }
    }

    private static void testPrepaidUserRental(User user, TinyScooter scooter) {
        System.out.println("Attempting rental with no balance...");
        if (!scooter.reserve(user)) {
            System.out.println("Failed as expected (no balance)");

            System.out.println("Adding balance of 1000 cents");
            user.deposit(1000);

            System.out.println("Retrying rental...");
            if (scooter.reserve(user)) {
                user.setVehiclesRented(scooter.getText());
                scooter.setLight(TinyScooter.LED.BLAU.name());
                simulateRental(user, scooter);
            }
        }
    }

    private static void testLicenseRestriction(User user, Scooter scooter) {
        System.out.println("Attempting to reserve scooter with car-only license");
        if (!scooter.reserve(user)) {
            System.out.println("Failed as expected (incorrect license)");
        }
    }

    private static void testCarRental(User user, Car car) {
        System.out.println("Attempting to reserve car");
        if (car.reserve(user)) {
            car.setSideLight(true);
            car.setLocked(true);
            user.setVehiclesRented(car.getText());

            System.out.println("Starting car rental...");
            if (car.start(user, car)) {
                simulateRental(user, car);
            }
        }
    }

    private static void testBikeRental(User user, Bike bike) {
        System.out.println("Attempting to rent bike (no license required)");
        if (bike.start(user, bike)) {
            user.setVehiclesRented(bike.getText());
            bike.setLocked(false);
            simulateRental(user, bike);
        }
    }

    private static void simulateRental(User user, Vehicle vehicle) {
        System.out.println("Rental active. Press Enter to end rental...");
        long start = System.currentTimeMillis();
        IOTools.readLine();
        long stop = System.currentTimeMillis();

        int timeCharge = ((IShareable)vehicle).getCurrentCharge(start, stop);
        int fee = calculateFee(vehicle, user.getVehiclesRented());
        int totalCharge = timeCharge * fee;

        // Calculate battery discharge based on vehicle type
        int dischargeRate;
        if (vehicle instanceof TinyScooter) {
            dischargeRate = 2;
        } else if (vehicle instanceof Bike) {
            dischargeRate = 1;
        } else if (vehicle instanceof Scooter) {
            dischargeRate = 4;
        } else if (vehicle instanceof Car) {
            dischargeRate = 6;
        } else {
            dischargeRate = 0;
        }

        if (vehicle instanceof Vehicle) {
            ((Vehicle)vehicle).updateBattery(timeCharge, dischargeRate);
        }

        System.out.println("Rental duration: " + timeCharge + " seconds");
        System.out.println("Fee per second: " + fee + " cents");
        System.out.println("Total charge: " + totalCharge + " cents");

        ((IShareable)vehicle).end(user, totalCharge);
        System.out.println("Rental ended. Updated user status:");
        user.getText();
        System.out.println("Updated vehicle status:");
        System.out.println(vehicle.getText());
    }

    private static int calculateFee(Vehicle vehicle, String rentedVehicle) {
        String provider = vehicle.provider;
        IProvider providerObj;

        if (provider.equals("A Bikes")) {
            providerObj = new Bikes();
        } else if (provider.equals("B Mobile")) {
            providerObj = new Mobile();
        } else {
            providerObj = new Share();
        }

        if (vehicle instanceof Car) {
            return providerObj.getCarFee();
        } else if (vehicle instanceof Scooter) {
            return providerObj.getScooterFee();
        } else {
            return providerObj.getNoLicenceFee();
        }
    }
}