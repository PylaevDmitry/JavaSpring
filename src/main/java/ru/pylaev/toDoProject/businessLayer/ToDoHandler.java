package ru.pylaev.toDoProject.businessLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.abstractions.IStorage;
import ru.pylaev.toDoProject.abstractions.IUserInterface;
import ru.pylaev.toDoProject.models.Task;

import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ToDoHandler implements Runnable {

    private final IStorage storage;
    private final IUserInterface ui;

    @Autowired
    public ToDoHandler (@Qualifier("consoleUserInterface") IUserInterface ui, @Qualifier("dbTasksDao") IStorage storage) {
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
            owner = ui.askInput(ToDoMain.properties.getPropertyContent("askOwner"));
        }
        storage.setOwner(owner);

        while (ui.isRunning()) {
            String finalOwner = owner;
            List<Task> list = Arrays.stream(storage.getAll()).filter(task -> !task.getStatus().equals("ARCH")).filter(task -> task.getOwner().equals(finalOwner)).collect(Collectors.toList());
            IntStream.range(0, list.size()).forEach(i -> ui.show(i + 1 + " " + list.get(i)));

            if (list.size()==0 || userInput.equals("NEW")) {
                userInput = ui.askInput(ToDoMain.properties.getPropertyContent("askNew"));
                if (!userInput.equals("BACK")) storage.add(new Task(owner, userInput, new Date(), "WAIT"));
            }
            else {
                userInput = ui.askInput(ToDoMain.properties.getPropertyContent("askNumber"));
                int taskIndex = getIndex(userInput, list);
                if (taskIndex != -1) do {
                    userInput = ui.askInput(ToDoMain.properties.getPropertyContent("askStatus"));
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
