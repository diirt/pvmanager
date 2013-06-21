/**
 * Copyright (C) 2010-12 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */
package org.epics.pvmanager.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.epics.pvmanager.WriteFunction;
import org.epics.pvmanager.service.ServiceMethod;
import org.epics.pvmanager.service.ServiceMethodDescription;
import org.epics.util.array.CircularBufferDouble;
import org.epics.vtype.VTable;
import org.epics.vtype.ValueFactory;

/**
 *
 * @author carcassi
 */
public class JDBCServiceMethod extends ServiceMethod {
    
    private final Connection connection;
    private final ExecutorService executorService;
    private final String query;
    private final List<String> parameterNames;

    public JDBCServiceMethod(JDBCServiceMethodDescription serviceMethodDescription) {
        super(serviceMethodDescription.serviceMethodDescription);
        this.connection = serviceMethodDescription.connection;
        this.executorService = serviceMethodDescription.executorService;
        this.query = serviceMethodDescription.query;
        this.parameterNames = serviceMethodDescription.orderedParameterNames;
    }

    private Connection getConnection() {
        return connection;
    }

    private ExecutorService getExecutorService() {
        return executorService;
    }
    private PreparedStatement preparedStatement;

    protected PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement == null) {
            preparedStatement = getConnection().prepareCall(getQuery());
        }
        return preparedStatement;
    }

    protected String getQuery() {
        return query;
    }
    
    private boolean isResultQuery() {
        return !getResultDescriptions().isEmpty();
    }

    protected List<String> getParameterNames() {
        return parameterNames;
    }

    @Override
    public void executeMethod(final Map<String, Object> parameters, final WriteFunction<Map<String, Object>> callback, final WriteFunction<Exception> errorCallback) {
        getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement preparedStatement = getPreparedStatement();
                    preparedStatement.clearParameters();
                    int i = 0;
                    for (String parameterName : getParameterNames()) {
                        preparedStatement.setObject(i, parameters.get(parameterName));
                    }
                    if (isResultQuery()) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        VTable table = resultSetToVTable(resultSet);
                        callback.writeValue(Collections.<String, Object>singletonMap(getResultDescriptions().keySet().iterator().next(), table));
                    } else {
                        callback.writeValue(new HashMap<String, Object>());
                    }
                } catch (Exception ex) {
                    errorCallback.writeValue(ex);
                }
            }
        });
    }

    static VTable resultSetToVTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int nColumns = metaData.getColumnCount();
        List<Class<?>> types = new ArrayList<>(nColumns);
        List<Object> data = new ArrayList<>(nColumns);
        List<String> names = new ArrayList<>(nColumns);
        for (int j = 1; j <= nColumns; j++) {
            names.add(metaData.getColumnName(j));
            switch (metaData.getColumnType(j)) {
                case Types.DOUBLE:
                case Types.FLOAT:
                case Types.INTEGER:
                case Types.TINYINT:
                case Types.BIGINT:
                case Types.SMALLINT:
                    types.add(double.class);
                    data.add(new CircularBufferDouble(Integer.MAX_VALUE));
                    break;
                    
                case Types.LONGNVARCHAR:
                case Types.VARCHAR:
                    types.add(String.class);
                    data.add(new ArrayList<String>());
                    break;
                    
                default:
                    throw new IllegalArgumentException("Unsupported type " + metaData.getColumnTypeName(j));

            }
        }
        
        while (resultSet.next()) {
            for (int i = 0; i < nColumns; i++) {
                Class<?> type = types.get(i);
                if (type.equals(String.class)) {
                    @SuppressWarnings("unchecked")
                    List<String> strings = (List<String>) data.get(i);
                    strings.add(resultSet.getString(i+1));
                } else if (type.equals(double.class)) {
                    ((CircularBufferDouble) data.get(i)).addDouble(resultSet.getDouble(i+1));
                }
            }
        }
        
        return ValueFactory.newVTable(types, names, data);
    }
}