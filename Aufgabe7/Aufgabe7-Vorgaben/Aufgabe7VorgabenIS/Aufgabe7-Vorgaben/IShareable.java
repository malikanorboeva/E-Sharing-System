package main.vehicles;

import main.user.User;

public interface IShareable {


    /**
     * Type of vehicle, e.g, car
     * @return name of type
     */
    public String getType();

    /**
     * Reseves vehicle for given user
     * @param user - user who wants to hire this vehicle
     * @return - true if successful
     */
    public boolean reserve(User user);

    /**
     * Starts hire
     * @param user - user who wants to hire this vehicle
     * @return - true if successful
     */
    public boolean start(User user);

    /**
     * Ends current hire and charges users balance.
     */
    public void end();

    /**
     * Current charge since start of hire
     * @return charge in cents
     */
    public int getCurrentCharge();

}
