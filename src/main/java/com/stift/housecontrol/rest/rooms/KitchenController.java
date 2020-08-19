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
import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/room/kitchen")
public class KitchenController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/eatingcorner", method = RequestMethod.GET)
    public boolean getMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
       return knxConnectionService.getLightState(Light.EG_KUECHE_ESSTISCH.getGroupAddress());
    }

    @RequestMapping(path = "light/eatingcorner", method = RequestMethod.PUT)
    public boolean switchMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.invertLightState(Light.EG_KUECHE_ESSTISCH.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/all", method = RequestMethod.GET)
    public boolean getAllSpontsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.getLightState(Light.EG_KUECHE_SPOTS_INSEL.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/all", method = RequestMethod.PUT)
    public boolean switchAllSpotsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        boolean state = knxConnectionService.getLightState(Light.EG_KUECHE_SPOTS_INSEL.getGroupAddress());

        if(state) {
            knxConnectionService.lightOff(Light.EG_KUECHE_ALLE_SPOTS.getGroupAddress());
            return false;
        }
        else {
            knxConnectionService.lightOn(Light.EG_KUECHE_ALLE_SPOTS.getGroupAddress());
            return true;
        }
    }

    @RequestMapping(path = "light/spots/front", method = RequestMethod.GET)
    public boolean getFrontSpontsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.getLightState(Light.EG_KUECHE_SPOTS_FRONT.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/front", method = RequestMethod.PUT)
    public boolean switchAllFrontSpotsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return  knxConnectionService.invertLightState(Light.EG_KUECHE_SPOTS_FRONT.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/middle", method = RequestMethod.GET)
    public boolean getMiddleSpontsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.getLightState(Light.EG_KUECHE_SPOTS_INSEL.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/middle", method = RequestMethod.PUT)
    public boolean switchMiddleSpotsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.invertLightState(Light.EG_KUECHE_SPOTS_INSEL.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/back", method = RequestMethod.GET)
    public boolean getBackSpontsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.getLightState(Light.EG_KUECHE_SPOTS_WAND.getGroupAddress());
    }

    @RequestMapping(path = "light/spots/back", method = RequestMethod.PUT)
    public boolean switchBackSpotsLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.invertLightState(Light.EG_KUECHE_SPOTS_WAND.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/all/open", method = RequestMethod.PUT)
    public void openMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open All Kitchen Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.KUECHE_ALLE.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/all/close", method = RequestMethod.PUT)
    public void closeMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Close All Kitchen Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.KUECHE_ALLE.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/134/open", method = RequestMethod.PUT)
    public void openkitchen134Jalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open 134 Kitchen Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.KUECHE_134.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/134/close", method = RequestMethod.PUT)
    public void closekitchen134Jalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Close 134 Kitchen Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.KUECHE_134.getGroupAddress());
    }


}
