/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */

package org.epics.vtype.seds;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.epics.vtype.Alarm;
import org.epics.vtype.Time;
import org.epics.vtype.VNumber;
import org.epics.vtype.VType;

/**
 * 
 * @author carcassi
 */
public class Seds2VType {

    public static VType toVType(JsonObject json) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public static JsonObject toJson(VType vType) {
        if (vType instanceof VNumber) {
            return toJson((VNumber) vType);
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public static JsonObject toJson(VNumber vNumber) {
        return new JsonVTypeBuilder()
                .add("value", vNumber.getValue().doubleValue())
                .addAlarm(vNumber)
                .addTime(vNumber)
                .build();
    }
}
