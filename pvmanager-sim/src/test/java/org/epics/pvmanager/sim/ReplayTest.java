/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.sim;

import java.util.List;
import org.epics.vtype.AlarmSeverity;
import org.epics.vtype.VDouble;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.epics.util.time.TimeDuration.*;
import static org.epics.pvmanager.test.TimeMatchers.*;
import org.epics.util.time.Timestamp;
import org.junit.BeforeClass;

/**
 * Tests uniform noise distribution function
 *
 * @author carcassi
 */
public class ReplayTest {

    @Test
    public void replayValues() {
        // Creates the function
        Timestamp start = Timestamp.now();
        Replay replay = (Replay) NameParser.createFunction("replay(\"./src/test/resources/org/epics/pvmanager/replay/parse1.xml\")");
        List<VDouble> values = replay.createValues(ofMillis(1000).after(start));
        assertThat(values.size(), equalTo(4));

        // Check first value
        VDouble value = values.get(0);
        assertThat(value.getValue(), equalTo(0.0));
        assertThat(value.getTimestamp(), equalTo(Timestamp.of(0, 0)));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.NONE));
        assertThat(value.getAlarmName(), equalTo("NONE"));
        assertThat(value.getTimeUserTag(), equalTo(0));
        assertThat(value.getLowerCtrlLimit(), equalTo(-10.0));
        assertThat(value.getLowerDisplayLimit(), equalTo(-10.0));
        assertThat(value.getLowerAlarmLimit(), equalTo(-9.0));
        assertThat(value.getLowerWarningLimit(), equalTo(-8.0));
        assertThat(value.getUpperWarningLimit(), equalTo(8.0));
        assertThat(value.getUpperAlarmLimit(), equalTo(9.0));
        assertThat(value.getUpperCtrlLimit(), equalTo(10.0));
        assertThat(value.getUpperDisplayLimit(), equalTo(10.0));

        // Check second value
        value = values.get(1);
        assertThat(value.getValue(), equalTo(1.0));
        assertThat(value.getTimestamp(), equalTo(Timestamp.of(0, 0).plus(ofMillis(100))));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.INVALID));
        assertThat(value.getAlarmName(), equalTo("RECORD"));
        assertThat(value.getTimeUserTag(), equalTo(0));

        // Check third value
        value = values.get(2);
        assertThat(value.getValue(), equalTo(2.0));
        assertThat(value.getTimestamp(), equalTo(Timestamp.of(0, 0).plus(ofMillis(200))));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.NONE));
        assertThat(value.getAlarmName(), equalTo("NONE"));
        assertThat(value.getTimeUserTag(), equalTo(0));

        // Check fourth value
        value = values.get(3);
        assertThat(value.getValue(), equalTo(3.0));
        assertThat(value.getTimestamp(), equalTo(Timestamp.of(0, 0).plus(ofMillis(500))));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.NONE));
        assertThat(value.getAlarmName(), equalTo("NONE"));
        assertThat(value.getTimeUserTag(), equalTo(0));
    }
    
    @BeforeClass
    public static void initializeParser() {
        Replay replay = (Replay) NameParser.createFunction("replay(\"./src/test/resources/org/epics/pvmanager/replay/parse2.xml\")");
        if (replay.hashCode() == 0)
            System.out.println("");
    }

    @Test
    public void adjustTime() {
        // Creates the function
        Timestamp start = Timestamp.now();
        Replay replay = (Replay) NameParser.createFunction("replay(\"./src/test/resources/org/epics/pvmanager/replay/parse2.xml\")");
        List<VDouble> values = replay.createValues(ofMillis(6000).after(start));
        assertThat(values.size(), equalTo(7));

        // Check first value
        VDouble value = values.get(0);
        assertThat(value.getValue(), equalTo(0.0));
        assertThat(value.getTimestamp(), within(ofMillis(1).around(start)));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.NONE));
        assertThat(value.getAlarmName(), equalTo("NONE"));
        assertThat(value.getTimeUserTag(), equalTo(0));
        assertThat(value.getLowerCtrlLimit(), equalTo(-10.0));
        assertThat(value.getLowerDisplayLimit(), equalTo(-10.0));
        assertThat(value.getLowerAlarmLimit(), equalTo(-9.0));
        assertThat(value.getLowerWarningLimit(), equalTo(-8.0));
        assertThat(value.getUpperWarningLimit(), equalTo(8.0));
        assertThat(value.getUpperAlarmLimit(), equalTo(9.0));
        assertThat(value.getUpperCtrlLimit(), equalTo(10.0));
        assertThat(value.getUpperDisplayLimit(), equalTo(10.0));

        // Check second value
        value = values.get(1);
        assertThat(value.getValue(), equalTo(1.0));
        assertThat(value.getTimestamp(), within(ofMillis(1).around(start.plus(ofMillis(1000)))));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.INVALID));
        assertThat(value.getAlarmName(), equalTo("RECORD"));
        assertThat(value.getTimeUserTag(), equalTo(0));

        // Check third value
        value = values.get(2);
        assertThat(value.getValue(), equalTo(2.0));
        assertThat(value.getTimestamp(), within(ofMillis(1).around(start.plus(ofMillis(2000)))));
        assertThat(value.getAlarmSeverity(), equalTo(AlarmSeverity.NONE));
        assertThat(value.getAlarmName(), equalTo("NONE"));
        assertThat(value.getTimeUserTag(), equalTo(0));
    }
}
