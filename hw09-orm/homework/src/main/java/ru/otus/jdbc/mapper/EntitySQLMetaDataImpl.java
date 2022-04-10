package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {

        this.entityClassMetaData = entityClassMetaData;
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();

        StringBuilder quest = new StringBuilder();
        for (int i = 0; i < fieldsWithoutId.size(); i++) {
            quest.append("?");
            if (i != (fieldsWithoutId.size() - 1)) {
                quest.append(",");
            }
        }

        String fieldsUpdate = getStringFields(entityClassMetaData.getFieldsWithoutId(), "=?");

        String fieldsInsert = getStringFields(fieldsWithoutId, "");

        this.selectAllSql = String.format("select * from %s",
                entityClassMetaData.getName());

        this.selectByIdSql = String.format("select id,%s from %s where id=?",
                fieldsInsert, entityClassMetaData.getName());

        this.insertSql = String.format("insert into %s (%s) VALUES (%s)",
                entityClassMetaData.getName(), fieldsInsert, quest.toString());

        this.updateSql = String.format("update %s set %s where %s=?",
                entityClassMetaData.getName(), fieldsUpdate, entityClassMetaData.getIdField().getName());

    }

    private String getStringFields(List<Field> listFields, String ext) {
        StringBuilder fields = new StringBuilder();
        int count;
        int size;
        size = listFields.size();
        count = 1;
        for (Object f : listFields) {
            var field = (Field) f;
            fields.append(field.getName() + ext);
            if (count != size) {
                fields.append(",");
            }
            count += 1;
        }
        return fields.toString();
    }

    public EntityClassMetaData getEntityClassMetaData() {
        return entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}
