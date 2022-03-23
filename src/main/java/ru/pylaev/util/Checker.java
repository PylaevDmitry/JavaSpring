package ru.pylaev.util;

import java.time.temporal.ValueRange;
import java.util.Arrays;

public class Checker {
    public static int isValidIndex (String userInput, int size) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            return  (ValueRange.of(1, size).isValidIntValue(taskIndex))?taskIndex:-1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int isValidInput (String userInput, String[] arrString) {
        boolean anyMatch = Arrays.stream(arrString).anyMatch(userInput::contains);
        if (userInput.length() > 0) {
            return (anyMatch)?1:-1;
        }
        else return 0;
    }
}
