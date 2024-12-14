package main.provider.main.provider;

import main.provider.main.IProvider;

public class Bikes extends Provider implements IProvider {
    @Override
    public String getName() {
        return "A Bikes";
    }

    @Override
    public int getMinCapacity() {
        return 20;
    }

    @Override
    public int getNoLicenceFee() {
        return 8;
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
