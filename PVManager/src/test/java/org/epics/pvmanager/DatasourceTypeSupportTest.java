/**
 * Copyright (C) 2010-12 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.epics.pvmanager;

import org.epics.pvmanager.DataSourceTypeAdapter;
import org.epics.pvmanager.DataSourceTypeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.epics.pvmanager.ValueCache;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author carcassi
 */
public class DatasourceTypeSupportTest {

    public DatasourceTypeSupportTest() {
    }

    public static DataSourceTypeAdapter<Class<?>, Object> createMockConverter(final Class<?> clazz) {
        return new DataSourceTypeAdapter<Class<?>, Object>() {

            @Override
            public int match(ValueCache<?> cache, Class<?> connection) {
                if (cache.getType().isAssignableFrom((Class<?>) connection)) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public Object getSubscriptionParameter(ValueCache<?> cache, Class<?> connection) {
                return null;
            }

            @Override
            public boolean updateCache(ValueCache<?> cache, Class<?> connection, Object message) {
                return false;
            }
        };
    }

    @Test
    public void find1() {
        DataSourceTypeSupport matcher = new DataSourceTypeSupport();
        Collection<DataSourceTypeAdapter<Class<?>, Object>> converters = new ArrayList<DataSourceTypeAdapter<Class<?>, Object>>();
        DataSourceTypeAdapter<Class<?>, Object> converter = createMockConverter(Double.class);
        converters.add(converter);
        DataSourceTypeAdapter<Class<?>, Object> matched = matcher.find(converters, new ValueCacheImpl<Number>(Number.class), Double.class);
        assertThat(matched, sameInstance(converter));
    }

    @Test(expected=IllegalStateException.class)
    public void find2() {
        DataSourceTypeSupport matcher = new DataSourceTypeSupport();
        Collection<DataSourceTypeAdapter<Class<?>, Object>> converters = new ArrayList<DataSourceTypeAdapter<Class<?>, Object>>();
        DataSourceTypeAdapter<Class<?>, Object> converter = createMockConverter(Double.class);
        converters.add(converter);
        DataSourceTypeAdapter<Class<?>, Object> matched = matcher.find(converters, new ValueCacheImpl<Number>(Number.class), String.class);
    }
}
