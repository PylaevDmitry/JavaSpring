package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {
    private final String fileName;

    public Generator (String fileName) {
        this.fileName = fileName;
    }

    public void generate (int n) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileName);
        Random random = new Random();
        printWriter.println(n);
        for (int i = 0; i < n; i++) {
            printWriter.print(random.nextInt() + " ");
        }
        printWriter.close();
    }
}
