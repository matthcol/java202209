package miniorm;

import data.Giraffe;
import data.Pilot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestSession {

    Session session;

    @BeforeEach
    void initData() {
        session = new Session();
    }

    @Test
    void testSavePersistent() {
        Pilot pilot = new Pilot(5.3);
        pilot.setFirstname("Buck");
        pilot.setLastname("Dany");
        pilot.setBirthdate(LocalDate.of(1950,11,1));

        session.save(pilot);
    }

    @Test
    void testSaveNonPersistent() {
        Giraffe giraffe = new Giraffe();
        assertThrows(IllegalArgumentException.class,
                () -> session.save(giraffe));
    }

}