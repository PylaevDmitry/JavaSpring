package ru.pylaev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputCheckerTest {

    @Test
    void isValidIndexNull () {
        Assertions.assertEquals(InputChecker.isValidIndex(null, 3), -1);
    }

    @Test
    void isValidIndexInRange () {
        Assertions.assertEquals(InputChecker.isValidIndex("1", 3), 1);
    }

    @Test
    void isValidIndexOutRange () {
        Assertions.assertEquals(InputChecker.isValidIndex("4", 3), -1);
    }

    @Test
    void isValidIndexBoard () {
        Assertions.assertEquals(InputChecker.isValidIndex("3", 3), 3);
    }

    @Test
    void isValidIndexZero () {
        Assertions.assertEquals(InputChecker.isValidIndex("0", 3), -1);
    }

    @Test
    void isValidIndexNegative () {
        Assertions.assertEquals(InputChecker.isValidIndex("-3", 3), -1);
    }

    @Test
    void isValidIndexNegativeLength () {
        Assertions.assertEquals(InputChecker.isValidIndex("-3", -3), -1);
    }

    @Test
    void inputInArrayOk() {
        assertEquals(InputChecker.inputInArray("DONE", new String[] {"ARCH", "WAIT", "DONE"}), 1);
    }

    @Test
    void inputInArrayNull() {
        assertEquals(InputChecker.inputInArray(null, new String[] {"ARCH", "WAIT", "DONE"}), 0);
    }

    @Test
    void inputInArrayReject() {
        assertEquals(InputChecker.inputInArray("noValid", new String[] {"ARCH", "WAIT", "DONE"}), -1);
    }

}