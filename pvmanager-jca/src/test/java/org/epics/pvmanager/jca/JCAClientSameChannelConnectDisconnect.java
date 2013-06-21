/**
 * Copyright (C) 2010-12 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager.jca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.epics.pvmanager.PVReader;
import org.epics.pvmanager.PVManager;
import org.epics.pvmanager.PVReaderListener;
import static org.epics.pvmanager.ExpressionLanguage.*;
import org.epics.pvmanager.PVReaderEvent;
import static org.epics.util.time.TimeDuration.*;

/**
 * 
 * @author carcassi
 */
public class JCAClientSameChannelConnectDisconnect {
    public static void main(String[] args) throws Exception {
        JCADataSource jca = new JCADataSource();
        PVManager.setDefaultDataSource(jca);
        
        String channelName = "TEST_1";
        String channelName2 = channelName + " {'longString':false}";
        
        List<String> names = Arrays.asList(channelName, channelName, channelName2, channelName2);
        List<PVReader<?>> pvs = new ArrayList<PVReader<?>>(); 
        for (String name : names) {
            pvs.add(null);
        }
        Random rand = new Random(1);
        final AtomicInteger connectCount = new AtomicInteger(0);
        final AtomicInteger valueNotificationCount = new AtomicInteger(0);
        final AtomicInteger connectionNotificationCount = new AtomicInteger(0);
        final AtomicInteger totalNotificationCount = new AtomicInteger(0);
        
        int countToPrint = 10;
        while (true) {
            
            int index = rand.nextInt(names.size());
            PVReader<?> pv = pvs.get(index);
            if (pv == null) {
                pv = PVManager.read(channel(names.get(index)))
                        .readListener(new PVReaderListener<Object>() {
                            @Override
                            public void pvChanged(PVReaderEvent<Object> event) {
                                if (event.isConnectionChanged()) {
                                    connectionNotificationCount.incrementAndGet();
                                }
                                if (event.isValueChanged()) {
                                    valueNotificationCount.incrementAndGet();
                                }
                                if (event.isExceptionChanged()) {
                                    System.out.println(event.getPvReader().lastException().getMessage());
                                }
                                totalNotificationCount.incrementAndGet();
                            }
                        })
                        .maxRate(ofHertz(100));
                pvs.set(index, pv);
                connectCount.incrementAndGet();
            } else {
                pv.close();
                pvs.set(index, null);
            }
            
            Thread.sleep(50);
            if (connectCount.get() == countToPrint) {
                System.out.println("Connections: " + connectCount + " Conn notifications: " + connectionNotificationCount 
                        + " Value notifications: " + valueNotificationCount + " Total notifications: " +totalNotificationCount);
                countToPrint += 10;
            }
        }
    }
}