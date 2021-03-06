/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.vtype;

import org.epics.pvmanager.vtype.SynchronizedVDoubleAggregator;
import org.epics.pvmanager.vtype.DataTypeSupport;
import org.epics.vtype.VDouble;
import static org.epics.vtype.ValueFactory.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.epics.pvmanager.vtype.DataTypeSupport;
import org.epics.pvmanager.vtype.SynchronizedVDoubleAggregator;
import org.epics.vtype.VDouble;
import static org.epics.util.time.TimeDuration.*;
import org.epics.util.time.Timestamp;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author carcassi
 */
public class SynchronizedVDoubleAggregatorTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        DataTypeSupport.install();
    }

    /**
     * Test of closestElement method, of class SynchronizedVDoubleAggregator.
     */
    @Test
    public void testClosestElement() {
        List<VDouble> data = new ArrayList<VDouble>();
        Timestamp reference = Timestamp.now();
        data.add(newVDouble(-2.0, newTime(reference.minus(ofMillis(5)))));
        data.add(newVDouble(-1.0, newTime(reference.minus(ofMillis(1)))));
        data.add(newVDouble(0.0, newTime(reference)));
        data.add(newVDouble(1.0, newTime(reference.plus(ofMillis(2)))));
        data.add(newVDouble(2.0, newTime(reference.plus(ofMillis(3)))));
        VDouble result = SynchronizedVDoubleAggregator.closestElement(data, ofMillis(10).around(reference), reference);
        assertEquals(0.0, result.getValue(), 0.000001);
    }

    @Test
    public void testClosestElement2() {
        List<VDouble> data = new ArrayList<VDouble>();
        Timestamp reference = Timestamp.now();
        data.add(newVDouble(-2.0, newTime(reference.minus(ofMillis(5)))));
        data.add(newVDouble(-1.0, newTime(reference.minus(ofMillis(1)))));
        data.add(newVDouble(1.0, newTime(reference.plus(ofMillis(2)))));
        data.add(newVDouble(2.0, newTime(reference.plus(ofMillis(3)))));
        VDouble result = SynchronizedVDoubleAggregator.closestElement(data, ofMillis(10).around(reference), reference);
        assertEquals(-1.0, result.getValue(), 0.000001);
    }
}