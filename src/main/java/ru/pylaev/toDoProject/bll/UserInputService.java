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

    private static final String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
    private static final String[] invalidNameSymbols = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private final TaskRepository taskRepository;

    @Autowired
    public UserInputService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getActualTasks (String owner) {
        return (taskRepository.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .collect(Collectors.toList());
    }

    public boolean checkOwner (String owner, String userInput) {
        return owner == null && (Checker.isValidInput(userInput, invalidNameSymbols) < 0);
    }

    public int getCurrentIndex(String userInput, int size) {
        if (size==0 || userInput.equals("NEW")) {
            return 0;
        }
        else if (!userInput.equals("BACK")) {
            var taskIndex = Checker.isValidIndex(userInput, size);
            if (taskIndex>-1) {
                return taskIndex;
            }
        }
        return -1;
    }

    public int saveNew (String owner, String userInput) {
        if (!userInput.equals("BACK")) {
            taskRepository.save(new Task(owner, userInput, new Date(), "WAIT"));
            return 1;
        }
        return 0;
    }

    public int changeStatus (String owner, String userInput, int taskIndex) {
        if (!userInput.equals("BACK")) {
            if (Checker.isValidInput(userInput, tasksStates)>0) {
                Task task = taskRepository.findById(getActualTasks(owner)
                        .get(taskIndex-1)
                        .getId())
                        .orElseThrow();
                task.setStatus(userInput);
                taskRepository.save(task);
                return 1;
            }
            return -1;
        }
        return 0;
    }
}
