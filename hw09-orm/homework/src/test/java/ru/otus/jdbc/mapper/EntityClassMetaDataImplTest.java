package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

class EntityClassMetaDataImplTest {

    @Test
    void getName() {
        EntityClassMetaDataImpl entityClassMetaData = new EntityClassMetaDataImpl(Client.class);
//        Assertions.assertNotEquals(entityClassMetaData.getName(), "Client");
    }

    @Test
    void getConstructor() {
    }

    @Test
    void getIdField() {
    }

    @Test
    void getAllFields() {
    }

    @Test
    void getFieldsWithoutId() {
    }
}