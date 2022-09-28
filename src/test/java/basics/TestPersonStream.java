package basics;

import data.Person;
import data.StatCountAvg;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TestPersonStream {
    static List<Person> persons;

    @BeforeAll
    static void initData() {
        persons = List.of(
                new Person(),
                new Person("John", "Doe", LocalDate.of(1900,12,31)),
                new Person("Jane", "Doe", LocalDate.of(1900,1,12)),
                new Person("Fred","Doe",LocalDate.of(2010,6,12)),
                new Person("Clara", "Doe", LocalDate.of(2011,7,13)),
                new Person("Wednesday","Addams", LocalDate.of(2010,6,6)),
                new Person("Morticia","Addams", LocalDate.of(1980,5,6)),
                new Person("Joe","Dalton", LocalDate.of(1977,5,6)),
                new Person("Jack","Dalton", LocalDate.of(1978,5,4)),
                new Person("William","Dalton", LocalDate.of(1979,5,6)),
                new Person("Averell","Dalton", LocalDate.of(1980,5,4))
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

    @Test
    void testGroupByFamily() {
        var families = persons.stream()
                .filter(p -> Objects.nonNull(p.getLastname()))
                .collect(Collectors.groupingBy(Person::getLastname));
        // System.out.println(families);
        for (var entryFamily: families.entrySet()){
            System.out.println(" - " + entryFamily.getKey() + ": ");
            for (var person: entryFamily.getValue()) {
                System.out.println("\t * " + person);
            }
        }
    }

    @Test
    void testGroupByFamilyOrderBy() {
        var families = persons.stream()
                .filter(p -> Objects.nonNull(p.getLastname()))
                .collect(Collectors.groupingBy(
                        Person::getLastname,
                        TreeMap::new,
                        Collectors.toCollection(() -> new TreeSet<>(
                                Comparator.comparing(Person::getBirthdate)
                        ))));
        // System.out.println(families);
        for (var entryFamily: families.entrySet()){
            System.out.println(" - " + entryFamily.getKey() + ": ");
            for (var person: entryFamily.getValue()) {
                System.out.println("\t * " + person);
            }
        }
    }

    // Exercice : group by lastname => stats (1 or summary)

    @Test
    void testSortPersons(){
        // persons.sort(???);
        // Collections.sort(persons); // compilation error
        var sortedPersons = persons.stream()
                .filter(p -> Objects.nonNull(p.getBirthdate()))
                        .collect(Collectors.toCollection(ArrayList::new));

        // sortedPersons.sort((p1,p2) -> -1);
        sortedPersons.sort(Comparator.comparing(Person::getBirthdate));
        System.out.println(sortedPersons);

        sortedPersons.sort(Comparator.comparing(Person::getBirthdate, Comparator.reverseOrder()));
        System.out.println(sortedPersons);

        // birth year desc, lastname asc, firstname asc
        sortedPersons.sort(
                Comparator.comparing((Person p) -> p.getBirthdate().getYear(),
                                Comparator.reverseOrder())
                        .thenComparing(Person::getLastname)
                        .thenComparing(Person::getFirstname));

        // inclus null
        var sortedAllPersons = new ArrayList<>(persons);
        sortedAllPersons.sort(
                Comparator.comparing(
                        Person::getBirthdate,
                        Comparator.nullsFirst(Comparator.naturalOrder())));

        // example calling generic function with explicit generic parameter
        Collections.<Person>sort(sortedPersons, Comparator.comparing(Person::getBirthdate));
    }

    @Test
    void statsAge() {
        //Predicate<OptionalInt> filter = o -> true;
        Predicate<OptionalInt> filter = OptionalInt::isEmpty;
        var stats = persons.stream()
                .map(Person::getAge)
                .filter(filter.negate())
                .mapToInt(OptionalInt::getAsInt)
                //.summaryStatistics();
                // .count();
                // .sum();
                // Optional results
                // .average();
                // .min();
                // .max();
                // Custom:
                //.reduce(0, (total, age) -> total + age);
                .reduce(0, Integer::sum);

                // Exercices : ecart type, variance

        System.out.println(stats);
    }

    @Test
    void statsCustom() {
        var stats = persons.stream()
                .map(Person::getAge)
                .filter(OptionalInt::isPresent)
                // .filter(o -> false)
                .mapToInt(OptionalInt::getAsInt)
                .collect(
                        StatCountAvg::new,
                        (StatCountAvg stat, int age) -> {
                            stat.setCount(stat.getCount() + 1);
                            stat.setAverage(stat.getAverage() + age);
                        },
                        (StatCountAvg stat1, StatCountAvg stat2) -> {
                            stat1.setCount(stat1.getCount() + stat2.getCount());
                            stat1.setAverage(stat1.getAverage() + stat2.getAverage());
                        });
        if (stats.getCount() == 0) {
            stats.setAverage(Double.NaN);
        } else {
            stats.setAverage(stats.getAverage() / stats.getCount());
        }
        System.out.println(stats);
    }



}
