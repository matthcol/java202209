package basics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestStringStream {

    @Test
    void testPipeline1() {
        var cities = List.of(
                "Toulouse", "Paris", "Lyon",
                "Marseille", "Bordeaux", "Nantes", "Lille");
        // pipeline 1
        System.out.println("**** Pipeline 1 ****");
        var cityCodes = cities.stream()
                .map(c -> c.toUpperCase())
                .filter(c -> c.contains("A"))
                .map(c -> c.substring(0,3))
                .collect(Collectors.toList());
        // cityCodes.add("ZZZ"); // Not Safe, not sure
        System.out.println(cityCodes);
        // pipeline 1bis : choix collection
        System.out.println();
        System.out.println("**** Pipeline 1 bis ****");
        var cityCodes2 = cities.stream()
                .map(String::toUpperCase)
                .filter(c -> c.contains("A"))
                .map(c -> c.substring(0,3))
                .collect(Collectors.toCollection(ArrayList::new));
        // cityCodes.add("ZZZ"); // Not Safe, not sure
        System.out.println(cityCodes);
        // pipeline 2
        System.out.println();
        System.out.println("**** Pipeline 2 ****");
        String letter = "A";
        var parcours = cities.stream()
                .map(String::toUpperCase)
                .filter(c -> c.contains(letter))
                .peek(c -> System.out.println("[Debug] " + c))
                .map(c -> c.substring(0,3))
                .collect(Collectors.joining(" -> "));
        System.out.println("Parcours: " + parcours);

        // pipeline with forEach (effet de bord)
        System.out.println();
        System.out.println("**** Pipeline 3 ****");
        cities.stream()
                .map(String::toUpperCase)
                .filter(c -> c.contains(letter))
                .map(c -> c.substring(0,3))
                .limit(2)
                .forEach(System.out::println);


        // nombre de lettres pour ecrire toutes les villes
        System.out.println();
        System.out.println("**** Pipeline 4 ****");
        var letterCount = cities.stream()
                .mapToInt(String::length)
                .sum();
        var letterCount2 = cities.stream()
                .collect(Collectors.summingInt(String::length));
        System.out.println("Letter count: " + letterCount
            + " / " + letterCount2);

    }
}







