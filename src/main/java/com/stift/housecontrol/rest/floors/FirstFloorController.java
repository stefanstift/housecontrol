package com.stift.housecontrol.rest.floors;

import com.stift.housecontrol.action.JalousieAction;
import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.model.Jalousie;
import com.stift.housecontrol.model.Light;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tuwien.auto.calimero.KNXException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/floor/first")
public class FirstFloorController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/all/off", method = RequestMethod.PUT)
    public void allLightsOff(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Turn all OG lights off Received");
        knxConnectionService.lightOff(Light.OG_ALL.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/open", method = RequestMethod.PUT)
    public void openMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open All Jalousies on ground floor Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.OG_ALL.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/close", method = RequestMethod.PUT)
    public void closeMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Close ALL Jalousies on ground floor Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.OG_ALL.getGroupAddress());
    }

}
