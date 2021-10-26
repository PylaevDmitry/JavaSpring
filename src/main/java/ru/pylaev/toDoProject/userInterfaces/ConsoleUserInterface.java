package ru.pylaev.toDoProject.userInterfaces;

import ru.pylaev.toDoProject.abstractions.IUserInterface;

import java.util.Scanner;

public class ConsoleUserInterface implements IUserInterface {

    private final Scanner _scanner;
    private volatile boolean running = true;

    public ConsoleUserInterface ( ) {
        _scanner = new Scanner((System.in)).useDelimiter("\n");
    }

    @Override
    public String askInput (String message) {
        System.out.println(message);
        var userInput = _scanner.next();
        if (userInput.equals("EXIT")) {
            System.out.println("Выход из программы...");
            running = false;
        }
        return userInput;
    }

    @Override
    public void show (String message) {
        System.out.println(message);
    }

    public boolean isRunning() {
        return running;
    }
}
