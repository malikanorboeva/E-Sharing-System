package main.provider.main.vehicles;

import main.provider.main.provider.Provider;

import javax.swing.*;

public class Vehicle {
    private static int nextID = 1;
    int id;
    String description;
    public int battery;
    public String provider;
    String type;
    int userID;

    public Vehicle(String description, String type, String provider, int battery) {
        this.id = nextID++;
        this.description = description;
        this.type = type;
        this.battery = battery;
        this.provider = provider;
    }

    public String getText() {
        String text = "[" + id + "] " + description + " (" + type + ") : " + "Provider: " + provider + ", Battery: " + battery + ", UserID: " + userID;
        return text;
    }

    public void updateBattery(int secondsUsed, int dischargeRate) {
        int dischargePct = secondsUsed * dischargeRate;
        battery = Math.max(0, battery - dischargePct);
    }

    protected boolean hasSufficientBattery(String providerName) {
        int minCapacity;
        switch(providerName) {
            case "A Bikes": minCapacity = 20; break;
            case "B Mobile": minCapacity = 25; break;
            case "C Share": minCapacity = 30; break;
            default: minCapacity = 30;
        }
        return battery >= minCapacity;
    }

}
