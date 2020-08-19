package com.stift.housecontrol.listener;

import com.stift.housecontrol.event.GroupAddressEvent;
import tuwien.auto.calimero.KNXFormatException;

public interface GroupAddressListener {

    /**
     * Triggered whenever a matching GA is read from someone
     * @param event
     */
    void readRequest(GroupAddressEvent event);
    /**
     * Triggered whenever a matching GA get's an answer to a preceding read request
     * @param event
     */
    void readResponse(GroupAddressEvent event);

    /**
     * Triggered whenever a matching HA is written by someone
     * @param event
     */
    void write(GroupAddressEvent event) throws KNXFormatException;


}
