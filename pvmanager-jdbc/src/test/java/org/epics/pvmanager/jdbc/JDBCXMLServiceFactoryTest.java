/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.jdbc;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import org.epics.pvmanager.ValueCache;
import org.epics.pvmanager.ChannelHandlerWriteSubscription;
import org.epics.pvmanager.WriteFunction;
import org.epics.pvmanager.WriteCache;
import org.epics.pvmanager.ChannelWriteCallback;
import org.epics.pvmanager.ChannelHandlerReadSubscription;
import org.epics.pvmanager.*;
import org.epics.pvmanager.service.Service;
import org.epics.vtype.VDouble;
import org.epics.vtype.VNumber;
import org.epics.vtype.VString;
import org.epics.vtype.VTable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

/**
 *
 * @author carcassi
 */
public class JDBCXMLServiceFactoryTest {

    @Test
    public void new1() throws Exception {
        File file = new File(getClass().getResource(".").toURI());
        JDBCXMLServiceFactory factory = new JDBCXMLServiceFactory(file);
        Collection<Service> services = factory.createServices();
        assertThat(services.size(), equalTo(1));
        Service service = services.iterator().next();
        assertThat(service.getName(), equalTo("jdbcSample"));
        assertThat(service.getDescription(), equalTo("A test service"));
        assertThat(service.getServiceMethods().size(), equalTo(2));
        assertThat(service.getServiceMethods().get("query").getName(), equalTo("query"));
        assertThat(service.getServiceMethods().get("query").getDescription(), equalTo("A test query"));
        assertThat(service.getServiceMethods().get("query").getArgumentDescriptions().size(), equalTo(0));
        assertThat(service.getServiceMethods().get("query").getResultTypes().get("result"), equalTo((Class) VTable.class));
        assertThat(service.getServiceMethods().get("query").getResultDescriptions().get("result"), equalTo("The query result"));
        assertThat(service.getServiceMethods().get("insert").getName(), equalTo("insert"));
        assertThat(service.getServiceMethods().get("insert").getDescription(), equalTo("A test insert query"));
        assertThat(service.getServiceMethods().get("insert").getResultTypes().size(), equalTo(0));
        assertThat(service.getServiceMethods().get("insert").getResultDescriptions().size(), equalTo(0));
        assertThat(service.getServiceMethods().get("insert").getArgumentDescriptions().get("name"), equalTo("The name"));
        assertThat(service.getServiceMethods().get("insert").getArgumentTypes().get("name"), equalTo((Class) VString.class));
        assertThat(service.getServiceMethods().get("insert").getArgumentDescriptions().get("index"), equalTo("The index"));
        assertThat(service.getServiceMethods().get("insert").getArgumentTypes().get("index"), equalTo((Class) VNumber.class));
        assertThat(service.getServiceMethods().get("insert").getArgumentDescriptions().get("value"), equalTo("The value"));
        assertThat(service.getServiceMethods().get("insert").getArgumentTypes().get("value"), equalTo((Class) VNumber.class));
    }

    @Test
    public void new2() throws Exception {
        File file = new File("DOES_NOT_EXISTS");
        JDBCXMLServiceFactory factory = new JDBCXMLServiceFactory(file);
        Collection<Service> services = factory.createServices();
        assertThat(services.size(), equalTo(0));
    }

}
