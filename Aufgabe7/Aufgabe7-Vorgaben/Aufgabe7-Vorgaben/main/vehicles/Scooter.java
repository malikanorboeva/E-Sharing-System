package main.provider.main.vehicles;

import main.provider.main.IShareable;
import main.provider.main.user.User;

public class Scooter extends Vehicle implements IShareable {

    boolean frontRearLight;
    boolean sideLight;

    public Scooter(String description, String type, String provider, int battery, boolean frontRearLight, boolean sideLight) {
        super(description, type, provider, battery);
        this.frontRearLight = frontRearLight;
        this.sideLight = sideLight;
    }

    String StrFrontRearLight() {
        if (frontRearLight==true) {
            return "ON";
        }
        return "OFF";
    }
    String StrSideLight() {
        if (sideLight==true) {
            return "ON";
        }
        return "OFF";
   }

    @Override
    public String getText() {
        String text = "[" + id + "] " + description + " (" + type + ") : " + "Provider: " + provider + ", Battery: " + battery + "%, UserID: " + userID + ", Front and Rear light: " + StrFrontRearLight() + ", Side light: " + StrSideLight();
        return text;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setFrontRearLight(boolean frontRearLight) {
        this.frontRearLight = frontRearLight;
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
        if (user.getLicense() != User.License.SCOOTER && user.getLicense() != User.License.SCOOTER_CAR) {
            System.out.println("Reservation failed: Invalid license");
            return false;
        }

        // Vehicle state check
        if (frontRearLight || sideLight) {
            System.out.println("Reservation failed: Vehicle not available");
            return false;
        }

        // Prepaid user balance check
        if (user.getTariff() == User.Tariff.PREPAID && user.getBalance() <= 0) {
            System.out.println("Reservation failed: Insufficient balance");
            return false;
        }

        // Reserve the vehicle
        setSideLight(true);
        userID = user.getId();
        return true;
    }

    public boolean start(User user, Scooter scooter) {
        if (user.getVehiclesRented().equals(scooter.getText())) {
            userID = user.getId();
            setFrontRearLight(true);
            setSideLight(false);      // Should turn off side light when rental starts
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
        setSideLight(false);
    }

    @Override
    public int getCurrentCharge(long start, long stop) {
        int timeinsec = (int) ((stop - start)/1000);
        return timeinsec;
    }
}
