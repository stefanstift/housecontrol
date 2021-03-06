package com.stift.housecontrol.rest.mess;

import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.mess.CurrentMeasurements;
import com.stift.housecontrol.model.Mess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.datapoint.Datapoint;
import tuwien.auto.calimero.datapoint.StateDP;
import tuwien.auto.calimero.dptxlator.DPTXlator2ByteFloat;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mess")
public class MessController {

    @Autowired
    private CurrentMeasurements currentMeasurements;
    @Autowired
    private KnxConnectionService knxConnectionService;

    @RequestMapping(path = "feeledtemp", method = RequestMethod.GET)
    public float getFeeledTemp(@Context HttpServletResponse response) {
        return currentMeasurements.getFeeledTemperature();
    }

    @RequestMapping(path = "temp", method = RequestMethod.GET)
    public float getTemp(@Context HttpServletResponse response) {
        return currentMeasurements.getTemperature();
    }

    @RequestMapping(path = "rain", method = RequestMethod.GET)
    public boolean getRain(@Context HttpServletResponse response) {
        return currentMeasurements.isRain();
    }

    @RequestMapping(path = "hell1", method = RequestMethod.GET)
    public float getHelligkeit1(@Context HttpServletResponse response) {
        return currentMeasurements.getHelligkeit1();
    }

    @RequestMapping(path = "date", method = RequestMethod.GET)
    public String getDate(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return (String) knxConnectionService.get3yteMess(Mess.DATE);
    }

    @RequestMapping(path = "windspeed", method = RequestMethod.GET)
    public float getWindSpeed(@Context HttpServletResponse response) throws KNXException, InterruptedException {
        return currentMeasurements.getWindSpeed();
    }



}
