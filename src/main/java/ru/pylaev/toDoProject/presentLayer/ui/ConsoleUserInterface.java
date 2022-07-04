package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ConsoleUserInterface extends UserInterface {
    private final Scanner _scanner;
    private volatile boolean running = true;

    @Autowired
    public ConsoleUserInterface (View view, UserInputService userInputService) {
        super(view, userInputService);
        _scanner = new Scanner((System.in)).useDelimiter("\n");
    }

    @Override
    public void run () {
        System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"));
        while (running) {
            var userInput = _scanner.next();
            if (userInput.equals("EXIT")) {
                System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("exitMessage"));
                running = false;
                break;
            }
            ViewHandler.processUserInput(userInput, view, userInputService);
            if (!Objects.isNull(view.getArrTasks())) {
                Arrays.stream(view.getArrTasks()).forEach(System.out::println);
            }
            System.out.println(view.getMessage());
        }
    }
}
