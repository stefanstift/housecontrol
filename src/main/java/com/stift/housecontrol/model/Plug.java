package com.stift.housecontrol.model;

import tuwien.auto.calimero.GroupAddress;

public enum Plug {

    BUERO_STEPHIE(1,3,2),
    BUERO_STEFAN(1,3,3);



    private final int g1;
    private final int g2;
    private final int g3;

    Plug(int g1, int g2, int g3) {
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
    }

    public GroupAddress getGroupAddress() {
        return new GroupAddress(this.g1, this.g2, this.g3);
    }


}
