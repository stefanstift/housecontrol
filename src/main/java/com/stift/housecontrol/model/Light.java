package com.stift.housecontrol.model;

import tuwien.auto.calimero.GroupAddress;

public enum Light {

    EG_ALL(1, 0, 0),
    EG_BAD_HAUPT (1, 1, 3),
    EG_BAD_DUSCHE(1, 1, 2),
    EG_BAD_SPIEGEL(1, 1, 4),
    EG_BUERO_HAUPT(1, 1, 14),

    EG_WC(1,1,15),

    EG_GARDEROBE(1,1,5),

    EG_EINGANG_HAUSTUER(1,1,6),
    EG_EINGANG_VORRAUM(1,1,7),


    EG_KUECHE_ESSTISCH(1, 1, 8),
    EG_KUECHE_ALLE_SPOTS(1, 1, 0),
    EG_KUECHE_SPOTS_FRONT(1, 1, 9),
    EG_KUECHE_SPOTS_INSEL(1, 1, 10),
    EG_KUECHE_SPOTS_WAND(1, 1, 11),

    EG_SPEIS(1,1,12),

    EG_WOHNZIMMER_HAUPT(1,1,13),

    EG_MUSIKZIMMER_HAUPT(1,1,16),

    EG_LICHT_TECHNIKRAUM(1,1,1),

    STIEGE(2,1,1),

    OG_ALL(3, 0, 0),
    OG_GANG(3,1,0),
    OG_SCHLAFZIMMER_HAUPT(3, 1, 1),
    OG_KINDERZIMMER1(3,1,2),
    OG_KINDERZIMMER2(3,1,3),
    OG_KINDERZIMMER3(3,1,4),
    OG_ABSTELLRAUM(3,1,5),
    OG_WC(3,1,8),
    OG_BAD_HAUPT(3,1,9),
    OG_BAD_DUSCHE(3,1,11),
    OG_BAD_SPIEGELSCHRANK(3,1,10),
    OG_SCHRANKRAUM(3,1,12),

    EG_OG_ALL(5,1,0);


    private final int g1;
    private final int g2;
    private final int g3;

    Light(int g1, int g2, int g3) {
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
    }

    public GroupAddress getGroupAddress() {
        return new GroupAddress(this.g1, this.g2, this.g3);
    }


}
