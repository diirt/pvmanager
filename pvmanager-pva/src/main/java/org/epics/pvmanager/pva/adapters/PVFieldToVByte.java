/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.pva.adapters;

import org.epics.pvdata.pv.PVField;
import org.epics.pvdata.pv.PVScalar;
import org.epics.pvdata.pv.PVStructure;
import org.epics.vtype.VByte;
import org.epics.vtype.VTypeToString;

/**
 * @author dkumar
 */
public class PVFieldToVByte extends AlarmTimeDisplayExtractor implements VByte {

	protected final Byte value;

	public PVFieldToVByte(PVStructure pvField, boolean disconnected) {
		this("value", pvField, disconnected);
	}

	public PVFieldToVByte(String fieldName, PVStructure pvField, boolean disconnected) {
		this(pvField.getSubField(fieldName), pvField, disconnected);
	}

	public PVFieldToVByte(PVField field, PVStructure pvParent, boolean disconnected) {
		super(pvParent, disconnected);

		if (field instanceof PVScalar)
			value = convert.toByte((PVScalar) field);
		else
			value = null;
	}

	@Override
	public Byte getValue() {
		return value;
	}

	@Override
	public String toString() {
		return VTypeToString.toString(this);
	}
}
