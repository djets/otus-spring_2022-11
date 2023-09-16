package ru.otus.spring.hw24.repository.event;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;
import ru.otus.spring.hw24.annotation.CascadeSave;

import java.lang.reflect.Field;
import java.util.Collection;

public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoOperations mongoOperations;

    CascadeCallback(final Object source, final MongoOperations mongoOperations) {
        this.source = source;
        this.setMongoOperations(mongoOperations);
    }

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class)
                        && field.isAnnotationPresent(CascadeSave.class)) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                final Iterable<Object> iterableFieldValue = (Iterable<Object>) field.get(getSource());
                if (iterableFieldValue != null) {
                    iterableFieldValue.forEach(object -> {
                        if (object != null) {
                            final FieldCallback callback = new FieldCallback();
                            ReflectionUtils.doWithFields(object.getClass(), callback);
                            getMongoOperations().save(object);
                        }
                    });
                }
            } else {
                final Object fieldValue = field.get(getSource());

                if (fieldValue != null) {
                    final FieldCallback callback = new FieldCallback();

                    ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

                    getMongoOperations().save(fieldValue);
                }
            }
        }
    }


    private Object getSource() {
        return source;
    }

    public void setSource(final Object source) {
        this.source = source;
    }

    private MongoOperations getMongoOperations() {
        return mongoOperations;
    }

    private void setMongoOperations(final MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}