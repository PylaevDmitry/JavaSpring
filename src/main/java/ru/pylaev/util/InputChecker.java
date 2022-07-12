package ru.pylaev.util;

import java.time.temporal.ValueRange;
import java.util.Arrays;

public class InputChecker {
    public static int isValidIndex (String userInput, int size) {
        if (size<1) return -1;
        try {
            int taskIndex = Integer.parseInt(userInput);
            return  (ValueRange.of(1, size).isValidIntValue(taskIndex))?taskIndex:-1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int inputInArray(String userInput, String[] arrString) {
        if (userInput==null) return 0;
        boolean anyMatch = Arrays.stream(arrString).anyMatch(userInput::contains);
        if (userInput.length() > 0) {
            return (anyMatch)?1:-1;
        }
        else return 0;
    }

    public static boolean checkInput(String input, String userInput, String[] invalidNameSymbols) {
        return input == null && (inputInArray(userInput, invalidNameSymbols) < 0);
    }
}
