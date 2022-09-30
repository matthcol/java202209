package miniorm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Session {

    public void save(Object object) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        Class<?> cls = object.getClass();
        checkPersistentClass(cls);

        // persist object in database
        // generate SQL INSERT
        String tablename = cls.getSimpleName();
        sqlBuilder.append(tablename)
                .append(" (");

        // NB: in v2, retrieve name from @

        // 1. retrieve fields to persist
        // INSERT INTO tablename (col1, col2, ..., coln)
        var fields = getPersistentField(cls);
        var colNames = concatColumnNames(fields);
        sqlBuilder.append(colNames)
                .append(") VALUES (");

        // 2. retrieve fields values
        // suite SQL: VALUES (val1, val2, ..., valn)
        var getterMethods = fields.stream()
                .map(Session::getterName)
                .peek(System.out::println)
                .map(gn -> Session.getMethodByNameNoArgs(cls, gn))
                .filter(Objects::nonNull)
                .toList();
        if (getterMethods.size() != fields.size()) {
            throw new IllegalArgumentException("class" + cls.getSimpleName()
                    + " is not a POJO");
        }

        var values = getterMethods.stream()
                .map(gm -> Session.invokeMethodNoArgs(gm, object))
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        // TODO: improve here to deal with placeholders and a list of value/type

        String sqlQuery = sqlBuilder.append(values)
                .append(")")
                .toString();
        System.out.println("SQL: " + sqlQuery);
    }

    public <T> List<T> getList(Class<T> cls) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");
        checkPersistentClass(cls);
        return null;
    }

    /**
     * check if class cls is annotated with annotation miniorm.Table
     *
     * @param cls
     * @throws IllegalArgumentException if cls is not annotated
     */
    private void checkPersistentClass(Class<?> cls) {
        if (!cls.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException(
                    "class " + cls.getSimpleName()
                            + " is not persistent");
        }
    }

    /**
     * get all fields (local and parent) from class cls
     *
     * @param cls
     * @return stream of fields
     */
    private static Stream<Field> getAllFields(Class<?> cls) {
        var localFieldStream = Arrays.stream(cls.getDeclaredFields());
        var superClass = cls.getSuperclass();
        if (Objects.nonNull(superClass)) {
            localFieldStream = Stream.concat(localFieldStream, getAllFields(superClass));
        }
        return localFieldStream;
    }

    /**
     *
     * @param cls
     * @return
     */
    private static List<Field> getPersistentField(Class<?> cls) {
        return getAllFields(cls)
                .filter(f -> f.isAnnotationPresent(Column.class))
                .toList();
    }

    /**
     *
     * @param fields
     * @return
     */
    private static String concatColumnNames(Collection<Field> fields) {
         return fields.stream()
                 .map(Field::getName)
                 .collect(Collectors.joining(", "));
    }

    /**
     * get getter method associated to a field with usual conventions
     * @param field
     * @return
     */
    private static String getterName(Field field) {
        // field name firstname (String) => getFirstname
        // field name fast (boolean) => isFast
        String basename = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        if ((field.getType() == boolean.class) || (field.getType() == Boolean.class)) {
            return "is" + basename;
        } else {
            return "get" + basename;
        }
    }

    private static List<Method> getGetters(Class<?> cls, Collection<? extends Field> fields) {
        var getterMethods = fields.stream()
                .map(Session::getterName)
                .peek(gn -> System.out.println("DEBUG [getter name] " + gn))
                .map(gn -> Session.getMethodByNameNoArgs(cls, gn))
                .filter(Objects::nonNull)
                .toList();
        if (getterMethods.size() != fields.size()) {
            throw new IllegalArgumentException("class" + cls.getSimpleName()
                    + " is not a POJO");
        }
        return getterMethods;
    }

    private static List<Method> getSetters(Class<?> cls, Collection<? extends Field> fields) {
//        var setterMethods = fields.stream()
//                .map(Session::setterName)
//                .peek(sn -> System.out.println("DEBUG [setter name] " + sn))
//                .map(sn -> Session.getMethodByNameNoReturn(cls, gn))
//                .filter(Objects::nonNull)
//                .toList();
//        if (getterMethods.size() != fields.size()) {
//            throw new IllegalArgumentException("class" + cls.getSimpleName()
//                    + " is not a POJO");
//        }
//        return getterMethods;
        return null;
    }

    /**
     *
     * @param cls
     * @param name
     * @return Method if found null otherwise
     */
    private static Method getMethodByNameNoArgs(Class<?> cls, String name) {
        try {
            return cls.getMethod(name);
        } catch (NoSuchMethodException|SecurityException e) {
            throw null;
        }
    }

    /**
     *
     * @param cls
     * @param name
     * @return Method if found null otherwise
     */
    private static Method getMethodByNameOneArg(Class<?> cls, String name, Class<?> typeArg) {
        try {
            return cls.getMethod(name, typeArg);
        } catch (NoSuchMethodException|SecurityException e) {
            throw null;
        }
    }

    private static Object invokeMethodNoArgs(Method method, Object object) {
        try {
            return method.invoke(object);
        } catch (InvocationTargetException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeMethodNoReturn(Method method, Object object, Object... args) {
        try {
            method.invoke(object, args);
        } catch (InvocationTargetException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
