package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    private T getNewObject(Constructor constructor, ResultSet rs) throws InstantiationException, IllegalAccessException, InvocationTargetException, SQLException {
        T t = (T) constructor.newInstance();
        for(Object f: entityClassMetaData.getFieldsWithoutId()){
            Field field = (Field) f;
            String fieldName = field.getName();
            field.setAccessible(true);
            field.set(t, rs.getString(fieldName));
        }
        Field fieldId = entityClassMetaData.getIdField();
        fieldId.setAccessible(true);
        fieldId.set(t, rs.getLong("id"));
        return t;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String selectByIdSql = entitySQLMetaData.getSelectByIdSql();
        Constructor constructor = entityClassMetaData.getConstructor();
        return dbExecutor.executeSelect(connection, selectByIdSql, List.of(id), rs -> {
            try {
                if (rs.next()) {
                    T t = getNewObject(constructor, rs);
                    return t;
                }
                return null;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        String select = entitySQLMetaData.getSelectAllSql();
        Constructor constructor = entityClassMetaData.getConstructor();
        return dbExecutor.executeSelect(connection, select, Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    T t = getNewObject(constructor, rs);
                    clientList.add(t);
                }
                return clientList;
            } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));    }

    @Override
    public long insert(Connection connection, T client) {
        String sql = entitySQLMetaData.getInsertSql();

        try {
            List<Object> param = new ArrayList<>();
            for (Object f: entityClassMetaData.getFieldsWithoutId()){
                Field field = (Field) f;
                field.setAccessible(true);
                Object o = field.get(client);
                param.add(o);
            }
            return dbExecutor.executeStatement(connection, sql, param);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        String sql = entitySQLMetaData.getUpdateSql();
        try {
            List<Object> params = new ArrayList<>();
            for(Object f: entityClassMetaData.getFieldsWithoutId()){
                Field field = (Field) f;
                String fieldName = field.getName();
                field.setAccessible(true);
                Object o = field.get(client);
                params.add(o);
            }
            Long id = (Long) entityClassMetaData.getIdField().get(client);
            params.add(id);
            dbExecutor.executeStatement(connection, sql, params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
