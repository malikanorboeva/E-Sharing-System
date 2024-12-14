package main.provider.main.vehicles;

import main.provider.main.IShareable;
import main.provider.main.user.User;
import static main.provider.main.vehicles.TinyScooter.LED.*;

public class TinyScooter extends Vehicle implements IShareable {

    public enum LED{
        ROT, GRÜN, GELB, BLAU
    }

    String light;

    public TinyScooter(String description, String type, String provider, int battery, LED led) {
        super(description, type, provider, battery);
        this.light = led.name();
    }

    public String getText() {
        String text = "[" + id + "] " + description + " (" + type + ") : " + "Provider: " + provider + ", Battery: " + battery + "%, UserID: " + userID + ", Light: " + light;
        return text;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setLight(String light) {
        this.light = light;
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

        // Check current state
        if (!light.equals("GRÜN")) {
            System.out.println("Reservation failed: Vehicle not available");
            return false;
        }

        // Prepaid user balance check
        if (user.getTariff() == User.Tariff.PREPAID && user.getBalance() <= 0) {
            System.out.println("Reservation failed: Insufficient balance");
            return false;
        }

        // Reserve the vehicle
        light = LED.GELB.name();
        userID = user.getId();
        return true;
    }

    public boolean start(User user, TinyScooter tinyScooter) {
        if (user.getVehiclesRented()==null) {
            System.out.println("Reservation failed.");
            return false;
        }
        else if (user.getVehiclesRented().equals(tinyScooter.getText())) {
            userID = user.getId();
            return true;
        }
        return false;
    }

    @Override
    public void end(User user, int charge) {
        int newBalance = user.getBalance()-charge;
        user.setBalance(newBalance);
        user.setVehiclesRented(null);
        setLight(GRÜN.name());
        userID = 0;
    }

    @Override
    public int getCurrentCharge(long start, long stop) {
        int timeinsec = (int) ((stop - start)/1000);
        return timeinsec;
    }
}
