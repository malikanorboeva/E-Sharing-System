package main.provider.main.user;

public class User {
    private static int nextId = 1;
    private int id;
    private String username;
    private int balance;
    public enum Tariff {
        STANDARD, PREPAID
    }
    public enum License {
        SCOOTER_CAR, SCOOTER, CAR,
    }
    private Tariff tariff;
    private License license;
    private String vehiclesRented;

    public User(String username, int balance, Tariff tariff, License license, String vehiclesRented) {
        this.id = nextId++;
        this.username = username;
        this.balance = balance;
        this.tariff = tariff;
        this.license = license;
        this.vehiclesRented = vehiclesRented;
    }

    public String getUsername() {
        return username;
    }
    public int getId() {
        return id;
    }
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public Tariff getTariff() {
        return tariff;
    }
    public License getLicense() {
        return license;
    }
    public String getVehiclesRented() {
        return vehiclesRented;
    }
    public void setVehiclesRented(String vehiclesRented) {
        this.vehiclesRented = vehiclesRented;
    }

    public void getText() {
        System.out.println(getId() + ": " + username + " (" + balance + " cents, " + getTariff() + " [" + getLicense() + "], " + vehiclesRented + ")");
    }

    public int deposit(int sum) {
        balance = balance + sum;
        return balance;
    }
}
