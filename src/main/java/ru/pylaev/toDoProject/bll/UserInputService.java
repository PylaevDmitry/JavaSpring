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

    private static final String askOwner = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    private final TaskRepository taskRepository;

    @Autowired
    public UserInputService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public View howToServe (View view, String userInput) {
        View resultView = new View();

        if (view.getMessage().equals(askOwner)) {
            resultView = checkOwner(view, userInput);
        }

        if (view.getMessage().equals(askNumber)) {
            resultView = processAskNumber(view, userInput);
        }
        else if (view.getMessage().equals(askNew)) {
            resultView = processAskNew(view, userInput);
        }
        else if (view.getMessage().equals(askStatus)) {
            resultView = processAskStatus(view, userInput);
        }

        return resultView;
    }

    private View checkOwner (View view, String userInput) {
        if (view.getOwner()==null && (Checker.isValidInput(userInput, invalidNameSymbols) < 0)) {
            view.setOwner(userInput);
            view.setMessage(askNumber);
        }
        return view;
    }

    private View processAskNumber (View view, String userInput) {
        List<Task> tasks = getActualTasks(view.getOwner());
        if (tasks.size()==0 || userInput.equals("NEW")) {
            view.setMessage(askNew);
        }
        else if (!userInput.equals("BACK")) {
            var taskIndex = Checker.isValidIndex(userInput, tasks.size());
            if (taskIndex != -1 && Checker.isValidInput(userInput, commands)<=0) {
                view.setMessage(askStatus);
                view.setTaskIndex(taskIndex);
            }
        }
        view.setTasks(tasks);
        return view;
    }

    private View processAskNew (View view, String userInput) {
        if (!userInput.equals("BACK")) {
            taskRepository.save(new Task(view.getOwner(), userInput, new Date(), "WAIT"));
        }
        view.setMessage(askNumber);
        view.setTasks(getActualTasks(view.getOwner()));
        return view;
    }

    private View processAskStatus (View view, String userInput) {
        if (!userInput.equals("BACK")) {
            if (Checker.isValidInput(userInput, tasksStates)>0) {
                Task task = taskRepository.findById(getActualTasks(view.getOwner()).get(view.getTaskIndex()-1).getId()).orElseThrow();
                task.setStatus(userInput);
                taskRepository.save(task);
                view.setMessage(askNumber);
                view.setTasks(getActualTasks(view.getOwner()));
            }
        }
        else {
            view.setMessage(askNumber);
        }
        return view;
    }

    private List<Task> getActualTasks (String owner) {
        return (taskRepository.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .collect(Collectors.toList());
    }
}
