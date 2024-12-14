package main.provider.main.provider;

import main.provider.main.IProvider;

public class Mobile extends Provider implements IProvider {
    @Override
    public String getName() {
        return "B Mobile";
    }

    @Override
    public int getMinCapacity() {
        return 25;
    }

    @Override
    public int getNoLicenceFee() {
        return 9;
    }

    @Override
    public int getScooterFee() {
        return 11;
    }

    @Override
    public int getCarFee() {
        return 15;
    }
}
