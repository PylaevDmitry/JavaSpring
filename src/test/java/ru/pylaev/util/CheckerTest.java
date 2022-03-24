package ru.pylaev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckerTest {

    @Test
    void isValidIndexNull () {
        Assertions.assertEquals(Checker.isValidIndex(null, 3), -1);
    }

    @Test
    void isValidIndexInRange () {
        Assertions.assertEquals(Checker.isValidIndex("1", 3), 1);
    }

    @Test
    void isValidIndexOutRange () {
        Assertions.assertEquals(Checker.isValidIndex("4", 3), -1);
    }

    @Test
    void isValidIndexBoard () {
        Assertions.assertEquals(Checker.isValidIndex("3", 3), 3);
    }

    @Test
    void isValidIndexZero () {
        Assertions.assertEquals(Checker.isValidIndex("0", 3), -1);
    }

    @Test
    void isValidIndexNegative () {
        Assertions.assertEquals(Checker.isValidIndex("-3", 3), -1);
    }

    @Test
    void isValidIndexNegativeLength () {
        Assertions.assertEquals(Checker.isValidIndex("-3", -3), -1);
    }
}