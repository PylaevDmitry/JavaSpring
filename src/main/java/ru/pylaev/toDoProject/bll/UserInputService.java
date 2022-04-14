package ru.pylaev.toDoProject.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.dal.repo.TaskRepository;
import ru.pylaev.util.Checker;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
@Service
@Scope("prototype")
public class UserInputService {

    private static final String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
    private static final String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
    private static final String[] invalidNameSymbols = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private final TaskRepository taskRepository;

    @Autowired
    public UserInputService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean checkOwner (String owner, String userInput) {
        return owner == null && (Checker.isValidInput(userInput, invalidNameSymbols) < 0);
    }

    public boolean processAskNumber (String userInput, int taskIndex) {
        return taskIndex != -1 && Checker.isValidInput(userInput, commands) <= 0;
    }

    public void processAskNew (String owner, String userInput) {
        if (!userInput.equals("BACK")) {
            taskRepository.save(new Task(owner, userInput, new Date(), "WAIT"));
        }
    }

    public boolean processAskStatus (String owner, String userInput, int taskIndex) {
        if (!userInput.equals("BACK")) {
            if (Checker.isValidInput(userInput, tasksStates)>0) {
                Task task = taskRepository.findById(getActualTasks(owner)
                        .get(taskIndex-1)
                        .getId())
                        .orElseThrow();
                task.setStatus(userInput);
                taskRepository.save(task);
                return true;
            }
        }
        return false;
    }

    public List<Task> getActualTasks (String owner) {
        return (taskRepository.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .collect(Collectors.toList());
    }
}
