package com.stift.housecontrol.rest.rooms;

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
@RequestMapping("/api/room/sleeping")
public class SleepingRoomController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/main", method = RequestMethod.GET)
    public boolean getMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
       return knxConnectionService.getLightState(Light.OG_SCHLAFZIMMER_HAUPT.getGroupAddress());
    }

    @RequestMapping(path = "light/main", method = RequestMethod.PUT)
    public boolean switchMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.invertLightState(Light.OG_SCHLAFZIMMER_HAUPT.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/main/open", method = RequestMethod.PUT)
    public void openMainJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.SCHLAFZIMMER_GROSS.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/main/close", method = RequestMethod.PUT)
    public void closeMainJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.SCHLAFZIMMER_GROSS.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/side/open", method = RequestMethod.PUT)
    public void openSideJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.SCHLAFZIMMER_KLEIN.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/side/close", method = RequestMethod.PUT)
    public void closeSideJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.SCHLAFZIMMER_KLEIN.getGroupAddress());
    }


}
