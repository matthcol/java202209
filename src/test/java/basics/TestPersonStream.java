package basics;

import data.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TestPersonStream {
    static List<Person> persons;

    @BeforeAll
    static void initData() {
        persons = List.of(
                new Person(),
                new Person("John", "Doe", LocalDate.of(1900,12,31)),
                new Person("Jane", "Doe", LocalDate.of(1900,1,12)),
                new Person("Fred","Doe",LocalDate.of(2010,6,12)),
                new Person("Clara", "Doe", LocalDate.of(2011,7,13))
        );
    }

    @Test
    void testPipelinePrint() {
        persons.forEach(System.out::println);
    }

    @Test
    void testPipelineAverageBirthYear() {
        var optAvgBirthYear = persons.stream()
                .map(Person::getBirthdate)
                .filter(Objects::nonNull)
                .mapToInt(LocalDate::getYear)
                .average();
        System.out.println(optAvgBirthYear);
    }

    @Test
    void testPipelineEmpty() {
        var optValue = persons.stream()
                .map(Person::getBirthdate)
                .filter(p -> false)
                .mapToInt(LocalDate::getYear)
                .average();
        System.out.println(optValue);


    }


}
