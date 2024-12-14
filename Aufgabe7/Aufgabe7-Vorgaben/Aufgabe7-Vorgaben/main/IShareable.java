package main.provider.main;

import main.provider.main.user.User;
import main.provider.main.vehicles.Vehicle;

public interface IShareable {


    /**
     * Type of vehicle, e.g, car
     * @return name of type
     */
    public String getType();

    /**
     * Reserves vehicle for given user
     * @param user - user who wants to hire this vehicle
     * @return - true if successful
     */
    public boolean reserve(User user);

    /**
     * Ends current hire and charges users balance.
     */
    public void end(User user, int charge);

    /**
     * Current charge since start of hire
     * @return charge in cents
     */
    public int getCurrentCharge(long start, long stop);

}
