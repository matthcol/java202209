package reflect;

import data.Person;
import data.Pilot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestViewModel {

    @Test
    void testViewObjectModel() {
        Pilot pilot = new Pilot(5.3);
        pilot.setFirstname("Buck");
        pilot.setLastname("Dany");
        ViewModel.viewObjectModel(pilot);
    }

    @Test
    void testViewModel() {
        ViewModel.viewModel(Person.class);
    }

}