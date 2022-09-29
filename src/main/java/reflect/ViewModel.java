package reflect;

import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ViewModel {

    static void viewObjectModel(Object o) {
        System.out.println("Reflect on object: " +o);
        viewModel(o.getClass());
    }

    static void viewModel(Class<?> cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Class simple name: " + cls.getSimpleName());
        System.out.println("Class name: " + cls.getPackageName());
        viewFields(cls.getFields(), "public");
        viewFields(cls.getDeclaredFields(), "declared");
        viewMethods(cls.getMethods(), "public");
        viewMethods(cls.getDeclaredMethods(), "declared");
        viewAnnotations(cls.getAnnotations(), "public", true, 0);
    }

    static void viewFields(Field[] fields, String label){
        System.out.println("Fields " + label);
        for (Field f: fields) {
            System.out.println("\t -f- " + f.getName());
            viewAnnotations(f.getAnnotations(), null, false, 1);
        }
    }

    static void viewMethods(Method[] methods, String label){
        System.out.println("Methods " + label);
        for (Method m: methods) {
            System.out.println("\t -m- " + m.getName());
        }
    }

    static void viewAnnotations(Annotation[] annotations,
                                String label, boolean showIntro, int levelIndent)
    {
        if (showIntro) System.out.println("Annotations " + label);
        for (Annotation annotation: annotations) {
            for (int i=0; i<levelIndent; i++) System.out.print("\t");
            System.out.println("\t -@- " + annotation.getClass().getName());
        }
    }
}
