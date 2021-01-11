package com.stift.housecontrol.connection;

import com.stift.housecontrol.action.JalousieAction;
import com.stift.housecontrol.model.Jalousie;
import com.stift.housecontrol.model.Mess;
import com.stift.housecontrol.service.GroupAddressListenerService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.datapoint.Datapoint;
import tuwien.auto.calimero.datapoint.StateDP;
import tuwien.auto.calimero.dptxlator.DPTXlator1BitControlled;
import tuwien.auto.calimero.dptxlator.DPTXlator2ByteFloat;
import tuwien.auto.calimero.dptxlator.DPTXlator8BitUnsigned;
import tuwien.auto.calimero.dptxlator.DPTXlatorDate;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXLinkClosedException;
import tuwien.auto.calimero.link.KNXNetworkLink;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

import java.net.InetSocketAddress;

@Service
public class KnxConnectionService implements InitializingBean {

    private static final InetSocketAddress server = new InetSocketAddress("knx.stift.me", KNXnetIPConnection.DEFAULT_PORT);
    private static final InetSocketAddress me = new InetSocketAddress("192.168.8.132", KNXnetIPConnection.DEFAULT_PORT);

    private KNXNetworkLink connection;

    @Autowired
    private GroupAddressListenerService groupAdressListenerService;

    public boolean getLightState(GroupAddress groupAddress) throws KNXException, InterruptedException {
        return getCommunicator().readBool(groupAddress);
    }

    public boolean invertLightState(GroupAddress groupAddress) throws KNXException, InterruptedException {
        boolean actualState = getLightState(groupAddress);
        boolean newState = !actualState;
        getCommunicator().write(groupAddress, newState);
        return newState;
    }

    public void lightOn(GroupAddress groupAddress) throws KNXException, InterruptedException {
        getCommunicator().write(groupAddress, true);
    }

    public void lightOff(GroupAddress groupAddress) throws KNXException, InterruptedException {
        getCommunicator().write(groupAddress, false);
    }


    public void setJalousiePosition(GroupAddress groupAddress, int percentage) throws KNXException {
        final Datapoint dp = new StateDP(groupAddress, groupAddress.toString());
        dp.setDPT(0, DPTXlator8BitUnsigned.DPT_SCALING.getID());
        getCommunicator().write(dp, String.valueOf(percentage));
    }

    public Object get2ByteMess(Mess mess) throws KNXException, InterruptedException {
        final Datapoint dp = new StateDP(mess.getGroupAddress(), mess.name());
        dp.setDPT(0, DPTXlator2ByteFloat.DPT_TEMPERATURE.getID());
        return getCommunicator().readNumeric(dp);
    }

    public Object getMess(Datapoint dp) throws KNXException, InterruptedException {
        return getCommunicator().readNumeric(dp);
    }

    public Object get3yteMess(Mess mess) throws KNXException, InterruptedException {
        final Datapoint dp = new StateDP(mess.getGroupAddress(), mess.name());
        dp.setDPT(0, DPTXlatorDate.DPT_DATE.getID());
        return getCommunicator().read(dp);
    }

    public Object getJalousiePercentage(Jalousie jalousie) throws KNXException, InterruptedException {
        final Datapoint dp = new StateDP(jalousie.getGroupAddress(), jalousie.name());
        dp.setDPT(0, DPTXlator8BitUnsigned.DPT_SCALING.getID());
        return getCommunicator().read(dp);
    }

    public void executeJalousie(JalousieAction action, GroupAddress groupAddress) throws KNXException {
       final Datapoint dp = new StateDP(groupAddress, action.name());
       dp.setDPT(0, DPTXlator1BitControlled.DPT_UPDOWN_CONTROL.getID());
       getCommunicator().write(dp, action.getCommand());
    }


    public void heartbeat() {
        getCommunicator();
    }

    private ProcessCommunicator getCommunicator() {
        try{
            return new ProcessCommunicatorImpl(connection);
        } catch (KNXLinkClosedException e) {
            ensureConnection();
            try {
                return new ProcessCommunicatorImpl(connection);
            } catch (KNXLinkClosedException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    private void ensureConnection() {
        if(connection == null || !connection.isOpen()) {
            try {
                connection = KNXNetworkLinkIP.newTunnelingLink(me, server, false, TPSettings.TP1);
                new ProcessCommunicatorImpl(connection).addProcessListener(groupAdressListenerService);
            } catch (KNXException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ensureConnection();
    }
}
