package ru.pylaev.toDoProject.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pylaev.toDoProject.dal.DAO;
import ru.pylaev.toDoProject.dal.fileIO.FileTasksDAO;
import ru.pylaev.toDoProject.dal.Task;
import ru.pylaev.util.InputChecker;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInputService {
    private static final String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
    private static final String[] invalidNameSymbols = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private final DAO taskDAO;

    @Autowired
    public UserInputService (FileTasksDAO tasksDAO) {
        this.taskDAO = tasksDAO;
    }

    public List<Task> getActualTasks (String owner) {
        return (taskDAO.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals("ARCH"))
                .collect(Collectors.toList());
    }

    public boolean checkOwner (String owner, String userInput) {
        return owner == null && (InputChecker.inputInArray(userInput, invalidNameSymbols) < 0);
    }

    public int getCurrentIndex(String userInput, int size) {
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

    public int saveNew (String owner, String userInput) {
        if (!userInput.equals("BACK")) {
            taskDAO.save(new Task(owner, userInput, new Date(), "WAIT"));
            return 1;
        }
        return 0;
    }

    public int changeStatus (String owner, String userInput, int taskIndex) {
        if (!userInput.equals("BACK")) {
            if (InputChecker.inputInArray(userInput, tasksStates)>0) {
                Task task = taskDAO.findById(getActualTasks(owner)
                        .get(taskIndex-1)
                        .getId())
                        .orElseThrow();
                task.setStatus(userInput);
                taskDAO.save(task);
                return 1;
            }
            return -1;
        }
        return 0;
    }
}
