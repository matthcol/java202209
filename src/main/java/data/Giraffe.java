package data;

import lombok.ToString;

@ToString
public class Giraffe extends Mammal {


    @Override
    public Giraffe whoAreYou() {
        return this;
    }
}
