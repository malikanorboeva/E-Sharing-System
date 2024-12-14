package main.provider;

public interface IProvider {

    /**
     * name of provider.
     * @return name of provider.
     */
    public String getName();

    /**
     * minimum capacity.
     * @return in percent
     */
    public int getMinCapacity();

    /**
     * Fee per second for vehicles without driving license
     * @return in ct per second
     */
    public int getNoLicenceFee();

    /**
     * Fee per second for scooters
     * @return in ct per second
     */
    public int getScooterFee();

    /**
     * Fee per second for cars
     * @return in ct per second
     */
    public int getCarFee();
}
