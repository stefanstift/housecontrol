package com.stift.housecontrol.rest.rooms;

import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.model.Light;
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
@RequestMapping("/api/room/stairs")
public class StairController {

    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "light/main", method = RequestMethod.GET)
    public boolean getMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
       return knxConnectionService.getLightState(Light.STIEGE.getGroupAddress());
    }

    @RequestMapping(path = "light/main", method = RequestMethod.PUT)
    public boolean switchMainOfficeLight(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return knxConnectionService.invertLightState(Light.STIEGE.getGroupAddress());
    }


}
