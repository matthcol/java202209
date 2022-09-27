package basics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestLoops {

    List<String> cities;

    @BeforeEach
    void initData() {
        cities = new ArrayList<>();
        Collections.addAll(cities,
                "Toulouse",
                "Muret",
                "Pau");
    }

    @Test
    void testLoopJ5to8(){
        System.out.println(cities);
        for (String city: cities) {
            System.out.println(" - " + city);
        }
        Iterator<String> it = cities.iterator();
        while (it.hasNext()) {
            String city = it.next();
            System.out.println(" * " + city);
        }
    }

    @Test
    void deleteItemWhileIterating() {
        String cityLook = "Pau";
        // delete all occurence of cityLook in cities

        // TODO

    }

    @Test
    void testLoopJ11(){
        var animals = List.of("Dog", "Cat", "Wolf");
        // NB: List.of(...) is Immutable
        // animals.clear();
        for (var animal: animals) {
            System.out.println(" - " + animal);
        }
        // same thing in functional mode
        animals.forEach(a -> System.out.println(" * " + a));
        animals.forEach((var a) -> System.out.println(" @ " + a));
        animals.forEach((String a) -> System.out.println(" # " + a));
    }

    @Test
    void loopOnArray() {
        String[] cars = { "Peugeot 208", "Renault 4L", "Ferrari F40"};
        String[] cars2 = new String[10]; // 10 empty items (null)
        System.out.println(Arrays.toString(cars));
        // for (int i=0; i<cars.length; i++) {}
        for (String car: cars) {
            System.out.println(" - " + car);
        }
        Arrays.stream(cars)
                .forEach(c -> System.out.println(" * " + c));
    }


}
