package com.stift.housecontrol.listener;

import com.stift.housecontrol.event.GroupAddressEvent;
import com.stift.housecontrol.mess.CurrentMeasurements;
import com.stift.housecontrol.model.Mess;
import com.stift.housecontrol.service.GroupAddressListenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tuwien.auto.calimero.KNXFormatException;

@Component
public class WindSpeedListener implements GroupAddressListener, InitializingBean {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupAddressListenerService listenerService;
    @Autowired
    private CurrentMeasurements currentMeasurements;


    @Override
    public void readRequest(GroupAddressEvent event) {

    }

    @Override
    public void readResponse(GroupAddressEvent event) {

    }

    @Override
    public void write(GroupAddressEvent event) throws KNXFormatException {
        LOGGER.info("Received WindSpeedListener {}", event.as2ByteFloat());
        currentMeasurements.setWindSpeed(event.as2ByteFloat());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        listenerService.addListener(Mess.WIND.getGroupAddress(), this);
    }
}
