/**
 * Copyright (C) 2010-12 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager.data;

import java.util.List;
import org.epics.util.array.ListNumber;

/**
 * Multi dimensional array, which can be used for waveforms or more rich data.
 * <p>
 * The data is stored in a linear structure. The sizes array gives the dimensionality
 * and size for each dimension.
 *
 * @param <T> the type for the multi channel values
 * @author carcassi
 */
public interface Array<T> {

    /**
     * Return the object containing the array data.
     * <p>
     * This method will either return a {@link List} or a {@link ListNumber}
     * depending of the array type. A collection is returned, instead of an
     * array, so that the type implementation can be immutable or can at
     * least try to prevent modifications. ListNumber has also several
     * advantages over the Java arrays, including the ability to iterate the list
     * regardless of numeric type.
     * 
     * @return 
     */
    Object getData();

    List<Integer> getSizes();
}
