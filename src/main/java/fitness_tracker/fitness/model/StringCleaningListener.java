package fitness_tracker.fitness.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.lang.reflect.Field;

public class StringCleaningListener
{

    @PrePersist
    @PreUpdate
    public void clean(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(entity);
                    if (value != null) {
                        field.set(entity, value.replace("\0", ""));
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}