package com.stift.housecontrol.service;

import com.stift.housecontrol.event.GroupAddressEvent;
import com.stift.housecontrol.listener.GroupAddressListener;
import org.springframework.stereotype.Service;
import tuwien.auto.calimero.DetachEvent;
import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.process.ProcessEvent;
import tuwien.auto.calimero.process.ProcessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupAddressListenerService implements ProcessListener {

    private final Map<String, List<GroupAddressListener>> listeners = new HashMap<>();


    public void addListener(GroupAddress ga, GroupAddressListener gal) {
        List<GroupAddressListener> list = new ArrayList<>();
        list.add(gal);
        listeners.put(ga.toString(), list);
    }


    @Override
    public void groupReadRequest(ProcessEvent e) {
        convertAndForward(e);
    }

    @Override
    public void groupReadResponse(ProcessEvent e) {
        convertAndForward(e);
    }

    @Override
    public void groupWrite(ProcessEvent e) {
        convertAndForward(e);
    }

    @Override
    public void detached(DetachEvent e) {
        // not of interest
    }

    private void convertAndForward(ProcessEvent e) {

        try {
            // convert
            GroupAddress destination = e.getDestination();
            GroupAddressEvent.Type type = GroupAddressEvent.Type.UNDEFINED;
            switch (e.getServiceCode()) {
                case /* GROUP_READ */ 0x0:
                    type = GroupAddressEvent.Type.GROUP_READ;
                    break;
                case /* GROUP_RESPONSE */ 0x40:
                    type = GroupAddressEvent.Type.GROUP_RESPONSE;
                    break;
                case /* GROUP_WRITE */ 0x80:
                    type = GroupAddressEvent.Type.GROUP_WRITE;
                    break;
            }

            GroupAddressEvent gae = new GroupAddressEvent(e.getSourceAddr().toString(), destination, type, e.getASDU());

            // forward
            synchronized (listeners) {
                if(listeners.containsKey(destination.toString())) {
                    for (GroupAddressListener listener : listeners.get(destination.toString())) {
                        switch (gae.getType()) {
                            case GROUP_READ:
                                listener.readRequest(gae);
                                break;
                            case GROUP_RESPONSE:
                                listener.readResponse(gae);
                                break;
                            case GROUP_WRITE:
                                listener.write(gae);
                                break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
