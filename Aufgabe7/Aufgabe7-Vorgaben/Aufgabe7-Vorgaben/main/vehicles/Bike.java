package main.provider.main.vehicles;

import main.provider.main.IShareable;
import main.provider.main.user.User;

public class Bike extends Vehicle implements IShareable {

    boolean locked;

    public Bike(String description, String type, String provider, int battery, boolean locked) {
        super(description, type, provider, battery);
        this.locked = locked;
    }

    public String getText() {
        String text = "[" + id + "] " + description + " (" + type + ") : " + "Provider: " + provider + ", Battery: " + battery + "%, UserID: " + userID + ", Locked: " + locked;
        return text;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String getType() {
        return type;
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

        // Vehicle state check
        if (locked) {
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
        userID = user.getId();
        return true;
    }

    public boolean start(User user, Bike bike) {
        if (bike.reserve(user)) {
            userID = user.getId();
            setLocked(false);  // Should unlock when rental starts
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
