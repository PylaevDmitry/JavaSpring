package ru.pylaev.toDoProject.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.pl.view.View;
import ru.pylaev.toDoProject.dal.repo.TaskRepository;
import ru.pylaev.util.Checker;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class UserInputService {

    private static final String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
    private static final String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
    private static final String[] invalidNameSymbols = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    private int taskIndex;
    private String owner;
    private final TaskRepository taskRepository;

    @Autowired
    public UserInputService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private List<Task> getActualTasks (String owner) {
        return (taskRepository.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .collect(Collectors.toList());
    }

    public View checkOwner (View view, String userInput) {
        if (owner==null && (Checker.isValidInput(userInput, invalidNameSymbols) < 0)) {
            owner = userInput;
            view.setMessage(askNumber);
        }
        return view;
    }

    public View processAskNumber (View view, String userInput) {
        List<Task> tasks = getActualTasks(owner);
        if (tasks.size()==0 || userInput.equals("NEW")) {
            view.setMessage(askNew);
        }
        else if (!userInput.equals("BACK")) {
            taskIndex = Checker.isValidIndex(userInput, tasks.size());
            if (taskIndex != -1 && Checker.isValidInput(userInput, commands)<=0) {
                view.setMessage(askStatus);
            }
        }
        view.setTasks(tasks);
        return view;
    }

    public View processAskNew (View view, String userInput) {
        if (!userInput.equals("BACK")) {
            taskRepository.save(new Task(owner, userInput, new Date(), "WAIT"));
        }
        view.setMessage(askNumber);
        view.setTasks(getActualTasks(owner));
        return view;
    }

    public View processAskStatus (View view, String userInput) {
        if (!userInput.equals("BACK")) {
            if (Checker.isValidInput(userInput, tasksStates)>0) {
                Task task = taskRepository.findById(getActualTasks(owner).get(taskIndex-1).getId()).orElseThrow();
                task.setStatus(userInput);
                taskRepository.save(task);
                view.setMessage(askNumber);
                view.setTasks(getActualTasks(owner));
            }
        }
        else {
            view.setMessage(askNumber);
        }
        return view;
    }
}
