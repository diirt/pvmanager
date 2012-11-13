/**
 * Copyright (C) 2010-12 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author carcassi
 */
class NewConnectionCollector implements Function<Boolean> {

    private final Object lock = new Object();
    private final Map<String, Boolean> channelConnected = new HashMap<>();
    private final Map<String, ConnectionWriteFunction> writeFunctions = new HashMap<>();
    private final Map<String, Integer> channelCount = new HashMap<>();
    private Boolean connected;
    
    private class ConnectionWriteFunction implements WriteFunction<Boolean> {
        
        private final String name;
        private int counter = 1;

        public ConnectionWriteFunction(String name) {
            this.name = name;
        }

        @Override
        public void setValue(Boolean newValue) {
            if (isClosed()) {
                throw new IllegalStateException("ConnectionCollector for '" + name + "' was closed.");
            }
            channelConnected.put(name, newValue);
            connected = null;
        }
        
        private void open() {
            counter++;
        }
        
        private boolean isClosed() {
            return counter == 0;
        }

        private void close() {
            counter--;
        }
        
    }

    public WriteFunction<Boolean> addChannel(final String name) {
        synchronized (lock) {
            if (channelConnected.containsKey(name)) {
                ConnectionWriteFunction writeFunction = writeFunctions.get(name);
                writeFunction.open();
                return writeFunction;
            } else {
                channelConnected.put(name, false);
                ConnectionWriteFunction writeFunction = new ConnectionWriteFunction(name);
                writeFunctions.put(name, writeFunction);
                connected = null;
                return writeFunction;
            }
        }
    }

    @Override
    public Boolean getValue() {
        synchronized (lock) {
            if (connected == null) {
                connected = calculate(channelConnected);
            }

            return connected;
        }
    }

    protected boolean calculate(Map<String, Boolean> channelConnected) {
        for (Boolean conn : channelConnected.values()) {
            if (conn != Boolean.TRUE) {
                return false;
            }
        }
        return true;
    }

    public void removeChannel(String channelName) {
        synchronized(lock) {
            ConnectionWriteFunction function = writeFunctions.get(channelName);
            if (function == null) {
                throw new IllegalArgumentException("Trying to remove channel '" + channelName + "' from ConnectionCollector, but it was already removed or never added.");
            } else {
                function.close();
                if (function.isClosed()) {
                    channelConnected.remove(channelName);
                    writeFunctions.remove(channelName);
                    connected = null;
                }
            }
        }
    }
}
