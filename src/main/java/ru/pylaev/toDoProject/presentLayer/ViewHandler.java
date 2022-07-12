package ru.pylaev.toDoProject.presentLayer;

import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.view.View;
import ru.pylaev.util.InputChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.pylaev.util.InputChecker.checkInput;

public class ViewHandler {
    private static int validateIndex(String userInput, int size) {
        if (size==0 || userInput.equals("NEW")) {
            return 0;
        }
        else if (!userInput.equals("BACK")) {
            var taskIndex = InputChecker.isValidIndex(userInput, size);
            if (taskIndex>-1) {
                return taskIndex;
            }
        }
        return -1;
    }

    public static List<Task> processUserInput(String userInput, View view, TaskRepository taskRepository) {
        List<Task> tasks = new ArrayList<>();
        if (Objects.isNull(userInput)) {
            return tasks;
        }
        else if (userInput.equals("EXIT")) {
            view.reset();
            return tasks;
        }

        if (view.getMessage().equals(Messages.askOwner) && checkInput(view.getOwner(), userInput, ToDoMain.invalidNameSymbols)) {
            view.setOwner(userInput);
        }

        switch (view.getMessage()) {
            case askNumber -> {
                List<Task> tasksList = taskRepository.findByOwner(view.getOwner());
                int index = validateIndex(userInput, tasksList.size());
                if (index == 0) view.setMessage(Messages.askNew);
                else if (index > 0) {
                    view.setMessage(Messages.askStatus);
                    view.setTaskIndex(index);
                }
                tasks = tasksList;
            }
            case askNew -> {
                int saveNewResult = taskRepository.saveNewTask(view.getOwner(), userInput);
                view.setMessage(Messages.askNumber);
                if (saveNewResult>0) tasks = taskRepository.findByOwner(view.getOwner());
            }
            case askStatus -> {
                int changeStatusResult = taskRepository.updateTaskStatus(view.getOwner(), userInput, view.getTaskIndex());
                if (changeStatusResult>0) {
                    view.setMessage(Messages.askNumber);
                    tasks = taskRepository.findByOwner(view.getOwner());
                }
                else if (changeStatusResult==0) view.setMessage(Messages.askNumber);
            }
        }
        return tasks;
    }
}
