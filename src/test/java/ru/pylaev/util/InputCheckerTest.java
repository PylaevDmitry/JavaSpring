package ru.pylaev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputCheckerTest {

    @Test
    void isValidIndexNull () {
        Assertions.assertEquals(-1, InputChecker.isValidIndex(null, 3));
    }

    @Test
    void isValidIndexInRange () {
        Assertions.assertEquals(1, InputChecker.isValidIndex("1", 3));
    }

    @Test
    void isValidIndexOutRange () {
        Assertions.assertEquals(-1, InputChecker.isValidIndex("4", 3));
    }

    @Test
    void isValidIndexBoard () {
        Assertions.assertEquals(3, InputChecker.isValidIndex("3", 3));
    }

    @Test
    void isValidIndexZero () {
        Assertions.assertEquals(-1, InputChecker.isValidIndex("0", 3));
    }

    @Test
    void isValidIndexNegative () {
        Assertions.assertEquals(-1, InputChecker.isValidIndex("-3", 3));
    }

    @Test
    void isValidIndexNegativeLength () {
        Assertions.assertEquals(-1, InputChecker.isValidIndex("-3", -3));
    }

    @Test
    void inputInArrayOk() {
        assertEquals(1, InputChecker.inputInArray("DONE", new String[] {"ARCH", "WAIT", "DONE"}));
    }

    @Test
    void inputInArrayNull() {
        assertEquals(0, InputChecker.inputInArray(null, new String[] {"ARCH", "WAIT", "DONE"}));
    }

    @Test
    void inputInArrayReject() {
        assertEquals(-1, InputChecker.inputInArray("noValid", new String[] {"ARCH", "WAIT", "DONE"}));
    }
}