package ru.otus.spring.hw15.repository.listener;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.otus.spring.hw15.annotation.CascadeSave;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Optional;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CascadingMongoEventListener<T> extends AbstractMongoEventListener<T> {

    MongoOperations mongoOperations;

    @Autowired
    public CascadingMongoEventListener(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<T> event) {
        T source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(DBRef.class) &&
                    field.isAnnotationPresent(CascadeSave.class)) {
                Object fieldValue = field.get(source);
                Optional.ofNullable(fieldValue)
                        .ifPresent(value -> {
                            DbRefFieldCallback callback = new DbRefFieldCallback();
                            ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                            if (!callback.isIdFound()) {
                                throw new MappingException("Cannot perform save o child object without id set");
                            }
                        });
            }
        });
    }

    @RequiredArgsConstructor
    public static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
        private boolean idFound;

        @Override
        public void doWith(@NotNull Field field) throws IllegalArgumentException {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }
}
