package data;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestPerson {

    @Test
    void testNoArgsConstructor() {
        var person = new Person();
        assertAll(
                () -> assertNull(person.getFirstname(), "firstname"),
                () -> assertNull(person.getLastname(), "lastname"),
                () -> assertNull(person.getBirthdate(), "birthdate"));
    }

    @Test
    void testAllArgsConstructor() {
        String firstname = "John";
        String lastname = "Doe";
        LocalDate birthdate = LocalDate.of(2000, 4, 14);
        var person = new Person(firstname, lastname, birthdate);
        assertAll(
                () -> assertEquals(firstname, person.getFirstname(), "firstname"),
                () -> assertEquals(lastname, person.getLastname(), "lastname"),
                () -> assertEquals(birthdate, person.getBirthdate(), "birthdate")
        );
    }

    @Test
    void testBuilderFirstnameLastnameNoBirthdate(){
        String firstname = "John";
        String lastname = "Doe";
        var person = Person.builder()
                .firstname(firstname)
                .lastname(lastname)
                .build();
        assertAll(
                () -> assertEquals(firstname, person.getFirstname(), "firstname"),
                () -> assertEquals(lastname, person.getLastname(), "lastname"),
                () -> assertNull(person.getBirthdate(), "birthdate")
        );
    }


}