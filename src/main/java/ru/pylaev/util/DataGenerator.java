package ru.pylaev.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class DataGenerator {
    private final Random random;

    public DataGenerator() {
        random = new Random();
    }

    public void generateToFile (String fileName, int n, int max) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.println(n);
        for (int i = 0; i < n; i++) {
            printWriter.print(random.nextInt(max) + " ");
        }
        printWriter.close();
    }

    public int[] generateToArray (int n, int max) {
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = random.nextInt(max);
        }
        return result;
    }
}
