package com.stift.housecontrol.task;

import com.stift.housecontrol.connection.KnxConnectionService;
import com.stift.housecontrol.model.Mess;
import tuwien.auto.calimero.KNXException;
import tuwien.auto.calimero.KNXTimeoutException;
import tuwien.auto.calimero.link.KNXLinkClosedException;

public class GetFeeledTempTask implements Runnable {

    private KnxConnectionService knxConnectionService;

    public GetFeeledTempTask(KnxConnectionService knxConnectionService) {
        this.knxConnectionService = knxConnectionService;
    }

    @Override
    public void run() {
        try {
            Object o = knxConnectionService.get2ByteMess(Mess.TEMP);
            System.out.println(o);
        } catch (KNXTimeoutException e) {
            e.printStackTrace();
        } catch (KNXLinkClosedException e) {
            e.printStackTrace();
        } catch (KNXException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
