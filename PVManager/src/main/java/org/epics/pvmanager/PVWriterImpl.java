/*
 * Copyright 2008-2011 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementation class for {@link PVWriter}.
 *
 * @author carcassi
 */
class PVWriterImpl<T> implements PVWriter<T> {
    
    static <T> PVWriterImpl<T> implOf(PVWriter<T> pvWriter) {
        if (pvWriter instanceof PVWriterImpl) {
            return (PVWriterImpl<T>) pvWriter;
        }
        
        throw new IllegalArgumentException("PVWriter must be implemented using PVWriterImpl");
    }

    private List<PVValueWriteListener> valueWriteListeners = new CopyOnWriteArrayList<PVValueWriteListener>();
    private volatile Exception lastWriteException;
    private volatile  WriteDirector<T> writeDirector;
    private final boolean syncWrite;

    PVWriterImpl(boolean syncWrite) {
        this.syncWrite = syncWrite;
    }
    
    void firePvValueWritten() {
        for (PVValueWriteListener listener : valueWriteListeners) {
            listener.pvValueWritten();
        }
    }

    void setWriteDirector(WriteDirector<T> writeDirector) {
        this.writeDirector = writeDirector;
        writeDirector.init();
    }

    /**
     * Adds a listener to the value. This method is thread safe.
     *
     * @param listener a new listener
     */
    @Override
    public void addPVValueWriteListener(PVValueWriteListener listener) {
        if (isClosed())
            throw new IllegalStateException("Can't add listeners to a closed PV");
        valueWriteListeners.add(listener);
    }

    /**
     * Removes a listener to the value. This method is thread safe.
     *
     * @param listener the old listener
     */
    @Override
    public void removePVValueChangeListener(PVValueWriteListener listener) {
        valueWriteListeners.remove(listener);
    }
    
    
    @Override
    public void write(T newValue) {
        if (syncWrite) {
            writeDirector.syncWrite(newValue, this);
        } else {
            writeDirector.write(newValue, this);
        }
    }

    // Theoretically, this should be checked only on the client thread.
    // Better to be conservative, and guarantee that another thread
    // cannot add a listener when another is closing.
    private AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * De-registers all listeners, stops all notifications and closes all
     * connections from the data sources needed by this. Once the PV
     * is closed, it can't be re-opened. Subsequent calls to close do not
     * do anything.
     */
    @Override
    public void close() {
        boolean wasClosed = closed.getAndSet(true);
        if (!wasClosed) {
            valueWriteListeners.clear();
            writeDirector.close();
        }
    }

    /**
     * True if no more notifications are going to be sent for this PV.
     *
     * @return true if closed
     */
    @Override
    public boolean isClosed() {
        return closed.get();
    }
    
    /**
     * Changes the last exception associated with write operations.
     * 
     * @param ex the new exception
     */
    void setLastWriteException(Exception ex) {
        lastWriteException = ex;
    }

    /**
     * Returns the last exception that was generated by write operations
     * and clears it (subsequent call will return null).
     *
     * @return the last generated exception or null
     */
    @Override
    public Exception lastWriteException() {
        return lastWriteException;
    }
    
}