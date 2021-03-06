/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.vtype;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.epics.vtype.ValueFactory.*;
import org.epics.util.array.ArrayDouble;
import org.epics.util.array.ArrayInt;
import org.epics.util.time.Timestamp;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author carcassi
 */
public class VTypeValueEqualsTest {
    
    public VTypeValueEqualsTest() {
    }

    @Test
    public void alarmEquals1() {
        assertThat(VTypeValueEquals.alarmEquals(newAlarm(AlarmSeverity.MINOR, "HIGH"), newAlarm(AlarmSeverity.MINOR, "HIGH")),
                equalTo(true));
        assertThat(VTypeValueEquals.alarmEquals(alarmNone(), alarmNone()),
                equalTo(true));
        assertThat(VTypeValueEquals.alarmEquals(alarmNone(), null),
                equalTo(false));
        assertThat(VTypeValueEquals.alarmEquals(null, alarmNone()),
                equalTo(false));
        assertThat(VTypeValueEquals.alarmEquals(null, null),
                equalTo(true));
        assertThat(VTypeValueEquals.alarmEquals(newAlarm(AlarmSeverity.MINOR, "HIGH"), newAlarm(AlarmSeverity.MINOR, "LOW")),
                equalTo(false));
        assertThat(VTypeValueEquals.alarmEquals(newAlarm(AlarmSeverity.MINOR, "HIGH"), newAlarm(AlarmSeverity.MAJOR, "HIGH")),
                equalTo(false));
    }

    @Test
    public void timeEquals1() {
        assertThat(VTypeValueEquals.timeEquals(newTime(Timestamp.of(12340000, 0)), newTime(Timestamp.of(12340000, 0))),
                equalTo(true));
        assertThat(VTypeValueEquals.timeEquals(newTime(Timestamp.of(12340000, 1)), newTime(Timestamp.of(12340000, 0))),
                equalTo(false));
        assertThat(VTypeValueEquals.timeEquals(newTime(Timestamp.of(12340000, 0), 12, true), newTime(Timestamp.of(12340000, 0), 12, true)),
                equalTo(true));
        assertThat(VTypeValueEquals.timeEquals(newTime(Timestamp.of(12340000, 0), 11, false), newTime(Timestamp.of(12340000, 0), 12, false)),
                equalTo(false));
        assertThat(VTypeValueEquals.timeEquals(newTime(Timestamp.of(12340000, 0), 12, true), newTime(Timestamp.of(12340000, 0), 12, false)),
                equalTo(false));
    }

    @Test
    public void typeEquals1() {
        assertThat(VTypeValueEquals.typeEquals(newVInt(0, alarmNone(), timeNow(), displayNone()),
                newVDouble(0.0)), equalTo(false));
        assertThat(VTypeValueEquals.typeEquals(newVInt(0, alarmNone(), timeNow(), displayNone()),
                newVInt(1, alarmNone(), timeNow(), displayNone())), equalTo(true));
        assertThat(VTypeValueEquals.typeEquals(newVString("Test", alarmNone(), timeNow()),
                newVDouble(0.0)), equalTo(false));
        assertThat(VTypeValueEquals.typeEquals(newVString("Test", alarmNone(), timeNow()),
                newVString("A", alarmNone(), timeNow())), equalTo(true));
    }
}
