package com.stift.housecontrol.scheduling;

import com.stift.housecontrol.connection.KnxConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tuwien.auto.calimero.KNXException;

@EnableScheduling
@Component
public class HeartbeatScheduler {

    @Autowired
    private KnxConnectionService knxConnectionService;


    @Scheduled(fixedDelay = 1000 * 60)
    public void heartbeat() throws KNXException, InterruptedException {
        System.out.println("hearbeat");
        knxConnectionService.heartbeat();
    }

}
