package main.provider.main.vehicles;

import main.provider.main.IShareable;
import main.provider.main.provider.Provider;
import main.provider.main.user.User;

public class Car extends Vehicle implements IShareable {

    boolean sideLight;
    boolean locked;

    public Car(String description, String type, String provider, int battery, boolean sideLight, boolean locked) {
        super(description, type, provider, battery);
        this.sideLight = sideLight;
        this.locked = locked;
    }

    @Override
    public String getText() {
        String text = "[" + id + "] " + description + " (" + type + ") : " + "Provider: " + provider + ", Battery: " + battery + "%, UserID: " + userID + ", Sidelight: " + sideLight + ", Locked: " + locked;
        return text;
    }

    @Override
    public String getType() {
        return type;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public void setSideLight(boolean sideLight) {
        this.sideLight = sideLight;
    }


    @Override
    public boolean reserve(User user) {
        // Basic checks first
        if (user == null || user.getVehiclesRented() != null) {
            System.out.println("Reservation failed: User invalid or already has a rental");
            return false;
        }

        // Battery check
        if (!hasSufficientBattery(provider)) {
            System.out.println("Reservation failed: Battery too low");
            return false;
        }

        // License check
        if (user.getLicense() != User.License.CAR && user.getLicense() != User.License.SCOOTER_CAR) {
            System.out.println("Reservation failed: Invalid license");
            return false;
        }

        // Vehicle state check
        if (sideLight) {
            System.out.println("Reservation failed: Vehicle not available");
            return false;
        }

        // Prepaid user balance check
        if (user.getTariff() == User.Tariff.PREPAID && user.getBalance() <= 0) {
            System.out.println("Reservation failed: Insufficient balance");
            return false;
        }

        // Reserve the vehicle
        setLocked(true);
        setSideLight(true);
        userID = user.getId();
        return true;
    }

    public boolean start(User user, Car car) {
        if (user.getVehiclesRented().equals(car.getText())) {
            userID = user.getId();
            setSideLight(false);
            setLocked(false);
            return true;
        }
        return false;
    }

    @Override
    public void end(User user, int charge) {
        int newBalance = user.getBalance()-charge;
        user.setBalance(newBalance);
        user.setVehiclesRented(null);
        userID = 0;
        setLocked(true);
    }

    @Override
    public int getCurrentCharge(long start, long stop) {
        int timeinsec = (int) ((stop - start)/1000);
        return timeinsec;
    }
}
