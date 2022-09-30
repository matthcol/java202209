package time;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataSource {

    public static Stream<Arguments> frenchDateSource() {
        return Stream.of(
                Arguments.of("12/07/1998", 1998, 7, 12),
                Arguments.of("29/02/2000", 2000, 2, 29),
                Arguments.of("01/06/2050", 2050, 6, 1)
        );
    }

}
