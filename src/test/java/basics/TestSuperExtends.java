package basics;

import data.Giraffe;
import data.Mammal;
import data.Person;
import data.Pilot;
import org.junit.jupiter.api.Test;
import util.StreamUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestSuperExtends {

    @Test
    void testSuper() {
        // scenario OK
        List<Mammal> mammals = new ArrayList<>(List.of(new Giraffe(), new Person(), new Pilot()));
        Collections.<Person>addAll(mammals, new Person(), new Person());
        // ? super Person ≡ Mammal

        // scenario NOK
        List<Pilot> pilots = new ArrayList<>(List.of(new Pilot(), new Pilot()));
        // Collections.addAll(pilots, new Person(), new Person()); // Error : // ? super Person ≢ Pilot
    }

    @Test
    void testExtends() {
        // scenario OK
        Set<Pilot> pilots = Set.of(new Pilot(), new Pilot(), new Pilot());
        List<Person> persons = new ArrayList<>(List.of(new Person(), new Person(), new Pilot(), new Person()));
        persons.addAll(pilots);
        // ? extends Person ≡ Pilot

        // scenario NOK
        List<Giraffe> giraffes = List.of(new Giraffe());
        // persons.addAll(giraffes); // Error: ? extends Person ≢ Giraffe
    }

    @Test
    void downCasting() {
        Set<Pilot> pilots = new HashSet<>(Set.of(new Pilot(), new Pilot(), new Pilot()));

        // no inheritance
        Pilot p = new Pilot(3.3);
        pilots.add(p);

        // with inheritance
        Person personOk = new Pilot(5.5);
        Person personNok = new Person();
        // pilots.add((Pilot) personOk); // OK: we could let this person go
        // pilots.add((Pilot) personNok); // Error : normal => impossible

        List<Person> candidates = List.of(personOk, personNok);
        for (Person candidate: candidates) {
            // System.out.println(candidate + " : " + candidate.getClass());
            if (candidate instanceof Pilot) {
                Pilot pilotCandidate = (Pilot) candidate;
                System.out.println(pilotCandidate.getAgility());
                pilots.add(pilotCandidate);
            }
        }
        System.out.println(pilots);


    }

    @Test
    void patternMatching() {
        List<Mammal> mammals = List.of(new Person(), new Pilot(), new Giraffe());
        for (Mammal mammal: mammals) {
            System.out.println("Mammal: " + mammal);
            switch (mammal){
                case Giraffe giraffe -> System.out.println("\t - I'm a Giraffe");
                case Pilot pilot -> System.out.println("\t - I'm a pilot with agility "
                        + pilot.getAgility());
                case Person person -> System.out.println("\t - I'm a person, I'd like to be a pilot");
                default -> System.out.println("\t - I'm another kind of mammal");
            }
        }

        // filter + downcasting
        var persons = mammals.stream()
                .filter(m -> m instanceof Person)
                .map(m -> (Person) m)
                .toList(); // Java 17 : shortcut for collect(Collectors.toList())
        System.out.println("Mammals who are a person: " + persons);

        // Objective:
        Stream<Mammal> mammalStream = mammals.stream();
        Stream<Person> personStream = StreamUtil.filterByType(mammalStream, Person.class);
        List<Person> persons2 = personStream
                .toList(); // Java 17 : shortcut for collect(Collectors.toList())
        System.out.println("Mammals who are a person: " + persons2);

    }
}
