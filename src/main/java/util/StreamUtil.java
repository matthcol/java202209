package util;

import data.Mammal;
import data.Person;

import java.util.stream.Stream;

public class StreamUtil {
    public static <T,U extends T> Stream<U> filterByType(Stream<T> generalStream, Class<U> specificClass) {
        return generalStream.filter(t -> specificClass.isInstance(t))
                .map(t -> specificClass.cast(t));
    }
}
