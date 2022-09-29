package miniorm;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Session {

    public void save(Object object){
        Class<?> cls = object.getClass();
        if (!cls.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException(
                        "class " + cls.getSimpleName()
                                + " is not persistent");
        }
        // persist object in database
        // generate SQL INSERT
        String tablename = cls.getSimpleName();
        // NB: in v2, retrieve name from @

        // 1. retrieve fields to persist
        // INSERT INTO tablename (col1, col2, ..., coln)
        getAllFields(cls)
                .filter(f -> f.isAnnotationPresent(Column.class))
                .forEach(System.out::println);


        // 2. retrieve fields values
        // suite SQL: VALUES (val1, val2, ..., valn)
    }

    public <T> List<T> getList(Class<T> cls) {
        return null;
    }

    private static Stream<Field> getAllFields(Class<?> cls) {
        var localFieldStream = Arrays.stream(cls.getDeclaredFields());
        var superClass = cls.getSuperclass();
        if (Objects.nonNull(superClass)) {
            localFieldStream = Stream.concat(localFieldStream, getAllFields(superClass));
        }
        return localFieldStream;
    }
}
