package com.stift.housecontrol.model;

import tuwien.auto.calimero.GroupAddress;

public enum Jalousie {

    EG_ALL (1, 2, 22),
    OG_ALL(3, 2, 0),
    EG_OG_ALL(5, 0, 0),


    KUECHE_134(1, 2, 20),
    KUECHE_ALLE(1,2,18),

    MUSIKZIMMER(1,2,16),

    BUERO(1, 2, 9),

    WOHNZIMMER_GROSS(1,2,11),
    WOHZIMMER_TUER(1,2,10),
    WOHZIMMER_ALLE(1,2,21),

    SCHLAFZIMMER_KLEIN(3,2,6),
    SCHLAFZIMMER_GROSS(3,2,7),

    KINDERTIMMER1(3,2,8),
    KINDERZIMMER2(3,2,9),
    KINDERZIMMER3(3,2,10),


    FASSADE_SUED(5, 0, 4),
    FASSADE_OST(5, 0, 3);


    private final int g1;
    private final int g2;
    private final int g3;

    Jalousie(int g1, int g2, int g3) {
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
    }

    public GroupAddress getGroupAddress() {
        return new GroupAddress(this.g1, this.g2, this.g3);
    }


}
