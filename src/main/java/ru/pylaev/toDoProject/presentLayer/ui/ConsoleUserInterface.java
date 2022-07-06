package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ConsoleUserInterface extends UserInterfaceBase {
    private final Scanner scanner = new Scanner((System.in)).useDelimiter("\n");

    @Autowired
    public ConsoleUserInterface (View view, UserInputService userInputService) {
        super(view, userInputService);
    }

    @Override
    public void showStartView() {
        System.out.println(view.getMessage());
    }

    @Override
    public void processUserInput() {
        var userInput = scanner.next();
        ViewHandler.processUserInput(userInput, view, userInputService);
        if (!Objects.isNull(view.getArrTasks())) {
            Arrays.stream(view.getArrTasks()).forEach(System.out::println);
        }
        System.out.println(view.getMessage());
    }
}
