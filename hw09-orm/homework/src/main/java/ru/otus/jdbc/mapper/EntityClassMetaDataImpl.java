package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.reflection.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EntityClassMetaDataImpl implements EntityClassMetaData{

    /*
    Reflection
    */

    private final Field idField;
    private final String name;
    private final Constructor constructor;
    private final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class aClass) {
        Field fieldId_ = null;
        this.allFields = new ArrayList<>();
        this.fieldsWithoutId = new ArrayList<>();

        Field[] fields = aClass.getDeclaredFields();
        for (Field f : fields) {
            this.allFields.add(f);
            if (f.isAnnotationPresent(Id.class)) {
                fieldId_ = f;
                continue;
            }
            this.fieldsWithoutId.add(f);
        }

        this.idField = fieldId_;
        this.name = aClass.getName().replace(aClass.getPackageName() + ".", "").toLowerCase(Locale.ROOT);
        Constructor constructor_ = null;
        Constructor[] constructors_ = aClass.getDeclaredConstructors();
        for (Constructor c: constructors_){
            if(c.getParameterCount() == 0){
                constructor_ = c;
                break;
            }
        }
        this.constructor = constructor_;
        if (this.idField == null) {
            logger.error("ERROR. Cannt find a Id {}.", aClass);
        }
        if (this.name == null) {
            logger.error("ERROR. Cannt find a Name {}.", aClass);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
