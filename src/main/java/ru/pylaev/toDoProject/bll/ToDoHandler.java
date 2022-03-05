package ru.pylaev.toDoProject.bll;

import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.dao.Dao;
import ru.pylaev.toDoProject.pl.presenter.Presenter;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ToDoHandler implements Runnable {

    private final Dao storage;
    private final Presenter ui;

    public ToDoHandler (Presenter ui, Dao storage) {
        this.storage = storage;
        this.ui = ui;
    }

    public void run () {
        String[] invalidNameSymbols = new String[]{" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};
        String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
        String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
        String userInput = "";
        String owner = "";

        while (inputCheck(invalidNameSymbols, owner)>=0) {
            owner = ui.askInput(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"));
        }
        storage.setOwner(owner);

        while (ui.isRunning()) {
            String finalOwner = owner;
            List<Task> list = Arrays.stream(storage.getAll()).filter(task -> !task.getStatus().equals("ARCH"))
                    .filter(task -> task.getOwner().equals(finalOwner)).collect(Collectors.toList());
            IntStream.range(0, list.size()).forEach(i -> ui.show(i + 1 + " " + list.get(i)));

            if (list.size()==0 || userInput.equals("NEW")) {
                userInput = ui.askInput(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew"));
                if (!userInput.equals("BACK")) storage.add(new Task(owner, userInput, new Date(), "WAIT"));
            }
            else {
                userInput = ui.askInput(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber"));
                int taskIndex = getIndex(userInput, list);
                if (taskIndex != -1) do {
                    userInput = ui.askInput(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus"));
                    if (inputCheck(tasksStates, userInput)>0) storage.setStatus(list.get(taskIndex-1).getId(),userInput);
                } while (inputCheck(commands, userInput)<=0);
            }
        }
    }

    private int getIndex (String userInput, List <Task> list) {
        try {
            int taskIndex = Integer.parseInt(userInput);
            return  (ValueRange.of(1, list.size()).isValidIntValue(taskIndex))?taskIndex:-1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int inputCheck (String[] arrString, String userInput) {
        boolean anyMatch = Arrays.stream(arrString).anyMatch(userInput::contains);
        if (userInput.length() > 0) {
            return (anyMatch)?1:-1;
        }
        else return 0;
    }
}
