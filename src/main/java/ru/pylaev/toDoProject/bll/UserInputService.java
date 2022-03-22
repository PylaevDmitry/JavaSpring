package ru.pylaev.toDoProject.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.pl.view.View;
import ru.pylaev.toDoProject.dal.repo.TaskRepository;

import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Scope("prototype")
public class UserInputService {

    private static final String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
    private static final String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
    private static final String[] invalidNameSymbols = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private int taskIndex;
    private String owner;
    private final View view = new View();
    private final TaskRepository taskRepository;

    @Autowired
    public UserInputService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean checkOwner (String userInput) {
        if (owner==null) {
            if (inputCheck(invalidNameSymbols, userInput) < 0) {
                owner = userInput;
            }
            else return false;
        }
        return true;
    }

    public View process (String userInput) {

        List<Task> list = ((List<Task>) taskRepository.findAll()).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .filter(task -> task.getOwner().equals(owner))
                .collect(Collectors.toList());

        if ((list.size()==0 && view.getMessage().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner")))
                || (userInput.equals("NEW") && view.getMessage().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber")))) {
            if (!userInput.equals("BACK")) {
                view.setMessage(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew"));
            }
            return view;
        }

        if (view.getMessage().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew"))) {
            if (!userInput.equals("BACK")) {
                taskRepository.save(new Task(owner, userInput, new Date(), "WAIT"));
            }
            view.setMessage(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber"));
        }

        else if (view.getMessage().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"))) {
            view.setMessage(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber"));
        }

        else if (view.getMessage().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber"))) {
            if (!userInput.equals("BACK")) {
                taskIndex = getIndex(userInput, list);
                if ((taskIndex != -1)&&(inputCheck(commands, userInput)<=0)) {
                    view.setMessage(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus"));
                }
            }
        }

        else if (view.getMessage().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus"))) {
            if (!userInput.equals("BACK")) {
                if (inputCheck(tasksStates, userInput)>0) {
                    Task task = taskRepository.findById(list.get(taskIndex-1).getId()).orElseThrow();
                    task.setStatus(userInput);
                    taskRepository.save(task);
                    view.setMessage(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber"));
                }
            }
            else {
                view.setMessage(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber"));
            }
        }

        List<Task> finalList = ((List<Task>) taskRepository.findAll()).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .filter(task -> task.getOwner().equals(owner))
                .collect(Collectors.toList());

        view.setArrTasks(new String[finalList.size()]);
        IntStream.range(0, finalList.size()).forEach(i -> view.getArrTasks()[i] = i + 1 + " " + finalList.get(i));

        return view;
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
