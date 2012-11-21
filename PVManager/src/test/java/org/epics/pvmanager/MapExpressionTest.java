/**
 * Copyright (C) 2010-12 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;
import org.epics.pvmanager.expression.Queue;
import static org.junit.Assert.*;
import org.junit.*;
import static org.epics.pvmanager.ExpressionLanguage.*;
import org.epics.pvmanager.expression.MapExpression;
import static org.epics.util.time.TimeDuration.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests map expression with a full pipeline.
 *
 * @author carcassi
 */
public class MapExpressionTest {

    public MapExpressionTest() {
    }

    @Before
    public void setUp() {
        pv = null;
        dataSource = new MockDataSource();
    }

    @After
    public void tearDown() throws InterruptedException {
        if (pv != null) {
            pv.close();
            pv = null;
        }
        
        Thread.sleep(400);
        
        assertThat(dataSource.getConnectedReadRecipes(), equalTo(Collections.<ChannelReadRecipe>emptyList()));
        assertThat(dataSource.getConnectedWriteRecipes(), equalTo(Collections.<ChannelWriteRecipe>emptyList()));
    }

    private volatile PVReader<?> pv;
    private MockDataSource dataSource;

    @Test
    public void map1() {
        MapExpression<Object> map = newMapOf(Object.class);
        pv = PVManager.read(map).from(dataSource).maxRate(ofMillis(100));
        map.add(latestValueOf(channel("test1")));
        assertThat(dataSource.getReadRecipe(), not(equalTo(null)));
        assertThat(dataSource.getConnectedReadRecipes(), hasSize(1));
        map.add(latestValueOf(channel("test2")));
        assertThat(dataSource.getReadRecipe(), not(equalTo(null)));
        assertThat(dataSource.getConnectedReadRecipes(), hasSize(2));
    }

    @Test
    public void map2() {
        MapExpression<Object> map = newMapOf(Object.class);
        pv = PVManager.read(map).from(dataSource).maxRate(ofMillis(100));
        map.add(latestValueOf(channel("test1")));
        map.add(latestValueOf(channel("test2")));
        assertThat(dataSource.getReadRecipe(), not(equalTo(null)));
        assertThat(dataSource.getConnectedReadRecipes(), hasSize(2));
        map.remove("test2");
        assertThat(dataSource.getConnectedReadRecipes(), hasSize(1));
    }

}