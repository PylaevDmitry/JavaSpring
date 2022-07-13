package ru.pylaev.toDoProject.businessLogicLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.util.InputChecker;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Scope("prototype")
public class TaskRepository {
    private static final String[] tasksStates = new String[] {
            ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusWait"),
            ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusDone"),
            ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusArch")
    };

    private final DAO taskDAO;

    @Autowired
    public TaskRepository(DAO tasksDAO) {
        this.taskDAO = tasksDAO;
    }

    public List<Task> findByOwner(String owner) {
        return (taskDAO.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusArch")))
                .collect(Collectors.toList());
    }

    public int saveNewTask(String owner, String userInput) {
        if (!userInput.equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("commandBack"))) {
            taskDAO.save(new Task(owner, userInput, new Date(), ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusWait")));
            return 1;
        }
        return 0;
    }

    public int updateTaskStatus(String owner, String userInput, int taskIndex) {
        if (!userInput.equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("commandBack"))) {
            if (InputChecker.inputInArray(userInput, tasksStates)>0) {
                Task task = taskDAO.findById(findByOwner(owner)
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
