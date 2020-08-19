package com.stift.housecontrol.rest.scenes;

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
@RequestMapping("/api/scene")
public class SceneController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    private boolean cinema = false;


    @RequestMapping(path = "goodnight", method = RequestMethod.PUT)
    public void goodNight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Scene Good Night Received");
        knxConnectionService.lightOff(Light.EG_OG_ALL.getGroupAddress());
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.EG_OG_ALL.getGroupAddress());
    }

    @RequestMapping(path = "goodmorning", method = RequestMethod.PUT)
    public void goodMorning(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Scene Good Morning Received -> nothing defined yet");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.SCHLAFZIMMER_GROSS.getGroupAddress());
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.SCHLAFZIMMER_KLEIN.getGroupAddress());
        // TODO - only execute if helligkeits value is < treshold
        //knxConnectionService.lightOn(Light.OG_GANG.getGroupAddress());
        //knxConnectionService.lightOn(Light.OG_SCHRANKRAUM.getGroupAddress());
        //knxConnectionService.lightOn(Light.OG_BAD_SPIEGELSCHRANK.getGroupAddress());
        //knxConnectionService.lightOn(Light.OG_WC.getGroupAddress());
    }

    @RequestMapping(path = "leave", method = RequestMethod.PUT)
    public void leave(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Scene Good Morning Received");
        knxConnectionService.lightOff(Light.EG_OG_ALL.getGroupAddress());
    }

    @RequestMapping(path = "home", method = RequestMethod.PUT)
    public void home(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Scene Come Home Received -> nothing defined yet");
    }

    @RequestMapping(path = "cinema", method = RequestMethod.PUT)
    public void cinema(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Scene Cinema Received");

        if(cinema) {
            knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.WOHZIMMER_TUER.getGroupAddress());
            knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.WOHNZIMMER_GROSS.getGroupAddress());
            knxConnectionService.lightOff(Light.EG_WOHNZIMMER_HAUPT.getGroupAddress());
            cinema = false;
        }
        else {
            knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.WOHZIMMER_TUER.getGroupAddress());
            knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.WOHNZIMMER_GROSS.getGroupAddress());
            cinema = true;
        }

    }

}
