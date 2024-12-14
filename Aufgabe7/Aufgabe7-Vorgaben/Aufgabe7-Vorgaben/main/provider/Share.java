package main.provider.main.provider;

import main.provider.main.IProvider;

public class Share extends Provider implements IProvider {
    @Override
    public String getName() {
        return "C Share";
    }

    @Override
    public int getMinCapacity() {
        return 30;
    }

    @Override
    public int getNoLicenceFee() {
        return 9;
    }

    @Override
    public int getScooterFee() {
        return 9;
    }

    @Override
    public int getCarFee() {
        return 15;
    }
}
