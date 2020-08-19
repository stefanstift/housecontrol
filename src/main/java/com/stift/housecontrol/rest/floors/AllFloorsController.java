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
@RequestMapping("/api/floor/all")
public class AllFloorsController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/off", method = RequestMethod.PUT)
    public void switchLightsOff(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        LOGGER.info("Switch ALL lights off received");
        knxConnectionService.lightOff(Light.EG_OG_ALL.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/open", method = RequestMethod.PUT)
    public void openMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Open All Jalousies on all floors Received");
        knxConnectionService.executeJalousie(JalousieAction.UP, Jalousie.EG_OG_ALL.getGroupAddress());
    }

    @RequestMapping(path = "jalousie/close", method = RequestMethod.PUT)
    public void closeMainOfficeJalousie(@Context HttpServletResponse response) throws KNXException {
        LOGGER.info("Close ALL Jalousies on all floors Received");
        knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.EG_OG_ALL.getGroupAddress());
    }

}
