package com.stift.housecontrol.scheduling;

import com.stift.housecontrol.connection.KnxConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import tuwien.auto.calimero.KNXException;

@EnableScheduling
@Component
public class LightScheduler {

    @Autowired
    private KnxConnectionService knxConnectionService;


  //  @Scheduled(fixedDelay = 10000)
    public void closeAll() throws KNXException, InterruptedException {
        //knxConnectionService.executeLight(LightAction.ON, SingleLight.EG.getGroupAddress());
    }

}
