package ru.pylaev.util;

import java.util.List;
import java.util.stream.IntStream;

public class ListToNumberingArrayConverter {
    public static <T> String[] convert(List<T> tasks) {
        String[] arrTasks = new String[tasks.size()];
        IntStream.range(0, tasks.size()).forEach(i -> arrTasks[i] = i + 1 + " " + tasks.get(i));
        return arrTasks;
    }
}
