package basics;

import org.junit.jupiter.api.Test;
import util.function.MyIntToDoubleFunction;

import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;

public class TestFunctionProfile {

    @Test
    void testComposeObject() {
        // NB: not optimized with primitive types !!
        Function<Integer, Double> f1 = x -> Math.sqrt(x);
        Function<Double, Boolean> f2 = d -> d > 10.3; // var possible
        Function<Integer, Boolean> f3 = f1.andThen(f2); // var possible
        boolean ok = f3.apply(5);
        System.out.println(ok);
    }

    @Test
    void testComposePrimitive() {
        // NB: not optimized with primitive types !!
        IntToDoubleFunction f1 = x -> Math.sqrt(x);
        DoublePredicate f2 = d -> d > 10.3;
        //var f3 = f1. ????? // no more andThen / compose
        boolean ok = f2.test(f1.applyAsDouble(5));
        System.out.println(ok);
    }

    @Test
    void testComposePrimitiveCustom() {
        // NB: not optimized with primitive types !!
        MyIntToDoubleFunction f1 = x -> Math.sqrt(x);
        DoublePredicate f2 = d -> d > 10.3;
        IntPredicate f3 = f1.andThen(f2);
        boolean ok = f3.test(5);
        System.out.println(ok);
    }
}
