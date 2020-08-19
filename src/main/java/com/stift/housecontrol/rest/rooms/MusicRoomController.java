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
@RequestMapping("/api/room/music")
public class MusicRoomController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/main", method = RequestMethod.GET)
    public boolean getMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
       return knxConnectionService.getLightState(Light.EG_MUSIKZIMMER_HAUPT.getGroupAddress());
    }

    @RequestMapping(path = "light/main", method = RequestMethod.PUT)
    public boolean switchMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        return knxConnectionService.invertLightState(Light.EG_MUSIKZIMMER_HAUPT.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/main/open", method = RequestMethod.PUT)
    public void openMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.MUSIKZIMMER.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/main/close", method = RequestMethod.PUT)
    public void closeMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Musikzimmer Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.MUSIKZIMMER.getGroupAddress());
    }


}
