package com.stift.housecontrol.model;

import tuwien.auto.calimero.GroupAddress;

public enum Mess {

    FEELED_TEMP(0, 0, 1),
    TEMP(0, 0, 2),
    HELLIGKEIT1(0,0,7),
    DATE(0,0,19),
    // in m/s
    WIND(0,0, 15),

    // boolean indicator
    REGEN(0,0,20);

    private final int g1;
    private final int g2;
    private final int g3;

    Mess(int g1, int g2, int g3) {
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
    }

    public GroupAddress getGroupAddress() {
        return new GroupAddress(this.g1, this.g2, this.g3);
    }


}
