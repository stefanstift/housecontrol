package com.stift.housecontrol.task;

import com.stift.housecontrol.action.JalousieAction;
import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.model.Jalousie;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.KNXTimeoutException;
import tuwien.auto.calimero.link.KNXLinkClosedException;

public class CloseAllJalousiesTask implements Runnable {

    private KnxConnectionService knxConnectionService;

    public CloseAllJalousiesTask(KnxConnectionService knxConnectionService) {
        this.knxConnectionService = knxConnectionService;
    }

    @Override
    public void run() {
        try {
            knxConnectionService.executeJalousie(JalousieAction.DOWN, Jalousie.EG_OG_ALL.getGroupAddress());
        } catch (KNXTimeoutException e) {
            e.printStackTrace();
        } catch (KNXLinkClosedException e) {
            e.printStackTrace();
        } catch (KNXException e) {
            e.printStackTrace();
        }
    }
}
