package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUserInterface extends UserInterfaceBase {
    private final Scanner scanner = new Scanner((System.in)).useDelimiter("\n");

    @Autowired
    public ConsoleUserInterface (View view, TaskRepository taskRepository) {
        super(view, taskRepository);
    }

    @Override
    public void showStartView() {
        System.out.println(view.getMessage());
    }

    @Override
    public void processUserInput() {
        var userInput = scanner.next();
        List<Task> tasks = ViewHandler.processUserInput(userInput, view, taskRepository);
        if (tasks.size()>0) {
            tasks.forEach(System.out::println);
        }
        System.out.println(view.getMessage());
    }
}
