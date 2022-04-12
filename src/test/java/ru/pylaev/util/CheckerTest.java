package ru.pylaev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckerTest {

    //
    @Test
            (null, 3, -1)
            (1, 3, -1)
            (-3, 3, -1)
    void isValidIndexNull (String userInput, int size, int expcted) {
        Assertions.assertEquals(Checker.isValidIndex(userInput, size), expcted);
    }
    }
}