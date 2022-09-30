package data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MammalTest {
    @Test
    void testWhoAreYou(){
        List<Mammal> mammals = List.of(new Giraffe(), new Pilot(), new Person());
        var res = mammals.stream()
                .map(Mammal::whoAreYou)
                .toList();

        var giraffe = new Giraffe();
        var res2 = giraffe.whoAreYou();

        var pilot = new Pilot();
        var res3 = pilot.whoAreYou();
        System.out.println(res3.getFirstname());
        System.out.println(res3.getAgility());
    }

}