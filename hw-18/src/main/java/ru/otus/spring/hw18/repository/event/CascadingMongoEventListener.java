package ru.otus.spring.hw18.repository.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.otus.spring.hw18.annotation.CascadeSave;

import java.lang.reflect.Field;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CascadingMongoEventListener extends AbstractMongoEventListener<Object> {
//
//    MongoOperations mongoOperations;
//
//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
//        Object source = event.getSource();
//        ReflectionUtils.doWithFields(source.getClass(), field -> {
//            ReflectionUtils.makeAccessible(field);
//            if (field.isAnnotationPresent(DBRef.class) &&
//                    field.isAnnotationPresent(CascadeSave.class)) {
//                Object fieldValue = field.get(source);
//                Optional.ofNullable(fieldValue)
//                        .ifPresent(value -> {
//                            DbRefFieldCallback callback = new DbRefFieldCallback();
//                            ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
//                            if (!callback.isIdFound()) {
//                                throw new MappingException("Cannot perform save o child object without id set");
//                            }
//                        });
//            }
//        });
//    }
//
//
//
//    public static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
//        private boolean idFound;
//
//        @Override
//        public void doWith(Field field) throws IllegalArgumentException {
//            ReflectionUtils.makeAccessible(field);
//
//            if (field.isAnnotationPresent(Id.class)) {
//                idFound = true;
//            }
//        }
//
//        public boolean isIdFound() {
//            return idFound;
//        }
//    }
}
