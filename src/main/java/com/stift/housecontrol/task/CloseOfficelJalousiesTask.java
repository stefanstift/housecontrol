package com.stift.housecontrol.task;

import com.stift.housecontrol.action.JalousieAction;
import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.model.Jalousie;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.KNXTimeoutException;
import tuwien.auto.calimero.link.KNXLinkClosedException;

public class CloseOfficelJalousiesTask implements Runnable {

    private KnxConnectionService knxConnectionService;

    public CloseOfficelJalousiesTask(KnxConnectionService knxConnectionService) {
        this.knxConnectionService = knxConnectionService;
    }

    @Override
    public void run() {
        try {
            System.out.println("close office");
            knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.BUERO.getGroupAddress());
        } catch (KNXTimeoutException e) {
            e.printStackTrace();
        } catch (KNXLinkClosedException e) {
            e.printStackTrace();
        } catch (KNXException e) {
            e.printStackTrace();
        }
    }
}
