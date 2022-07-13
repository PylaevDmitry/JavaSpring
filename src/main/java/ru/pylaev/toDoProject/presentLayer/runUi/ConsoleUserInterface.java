package ru.pylaev.toDoProject.presentLayer.runUi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.businessLogicLayer.StateService;

import java.util.Arrays;
import java.util.Scanner;

@Component
public class ConsoleUserInterface extends RunUI {
    private final Scanner scanner = new Scanner((System.in)).useDelimiter("\n");

    @Autowired
    public ConsoleUserInterface (State state) {
        super(state);
    }

    @Override
    public void showStartView() {
        System.out.println(view.getMessage());
    }

    @Override
    public void processUserInput() {
        view.setTasks(StateService.processUserInput(scanner.next(), state));
        view.setMessage(state.getStep().toString());
        Arrays.stream(view.getTasks()).forEach(System.out::println);
        System.out.println(view.getMessage());
    }
}
