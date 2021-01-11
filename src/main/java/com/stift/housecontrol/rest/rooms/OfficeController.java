package com.stift.housecontrol.rest.rooms;

import com.stift.housecontrol.action.JalousieAction;
import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.model.Jalousie;
import com.stift.housecontrol.model.Light;
import com.stift.housecontrol.model.Plug;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tuwien.auto.calimero.KNXException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/room/office")
public class OfficeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/main", method = RequestMethod.GET)
    public boolean getMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("GET Main Office Light State");
        return knxConnectionService.getLightState(Light.EG_BUERO_HAUPT.getGroupAddress());
    }

    @RequestMapping(path = "light/main", method = RequestMethod.PUT)
    public boolean switchMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Switch Main Office Light Received");
        return knxConnectionService.invertLightState(Light.EG_BUERO_HAUPT.getGroupAddress());
    }

    @RequestMapping(path = "plug/stephie", method = RequestMethod.GET)
    public boolean getStephiePlug(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("GET Stephie Office Plug State");
        return knxConnectionService.getLightState(Plug.BUERO_STEPHIE.getGroupAddress());
    }

    @RequestMapping(path = "plug/stephie", method = RequestMethod.PUT)
    public boolean switchStephiePlug(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Switch Stephie Office Plug Received");
        return knxConnectionService.invertLightState(Plug.BUERO_STEPHIE.getGroupAddress());
    }

    @RequestMapping(path = "plug/stefan", method = RequestMethod.GET)
    public boolean getStefanPlug(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("GET Stefan Office Plug State");
        return knxConnectionService.getLightState(Plug.BUERO_STEFAN.getGroupAddress());
    }

    @RequestMapping(path = "plug/stefan", method = RequestMethod.PUT)
    public boolean switchStefanPlug(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Switch Stefan Office Plug Received");
        return knxConnectionService.invertLightState(Plug.BUERO_STEFAN.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/main/position/{position}", method = RequestMethod.PUT)
    public void openMainOfficeJalousie(@PathVariable ("position") int position, @Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Set Main Office Jalousie Position to {} received", position);
        Preconditions.checkState(position >= 0 && position <= 100);
        knxConnectionService.setJalousiePosition(Jalousie.FASSADE_OST.getGroupAddress(), position);
    }


    @RequestMapping(path = "jalousie/main/open", method = RequestMethod.PUT)
    public void openMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Office Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.BUERO.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/main/close", method = RequestMethod.PUT)
    public void closeMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open Main Office Jalousie Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.BUERO.getGroupAddress());
    }


}
