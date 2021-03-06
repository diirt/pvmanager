/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.integration;

import java.util.List;

/**
 *
 * @author carcassi
 */
public interface Validator {
    
    public List<String> validate(List<Object> values);
}
