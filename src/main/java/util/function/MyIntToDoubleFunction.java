package util.function;

import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;

@FunctionalInterface
public interface MyIntToDoubleFunction {
    double apply(int i);

    default IntPredicate andThen(DoublePredicate f) {
        return i -> f.test(this.apply(i));
    }
}
