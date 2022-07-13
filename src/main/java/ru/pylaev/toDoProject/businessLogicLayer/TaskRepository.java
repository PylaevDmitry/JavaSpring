package ru.pylaev.toDoProject.businessLogicLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.util.InputChecker;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
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

    public synchronized List<Task> findByOwner(String owner) {
        return (taskDAO.findByOwner(owner)).stream()
                .filter(task -> !task.getStatus().equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusArch")))
                .collect(Collectors.toList());
    }

    public synchronized int saveNewTask(String owner, String taskContent) {
        if (!taskContent.equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("commandBack"))) {
            taskDAO.save(new Task(owner, taskContent, new Date(), ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("statusWait")));
            return 1;
        }
        return 0;
    }

    public synchronized int updateTask(String owner, String status, int taskIndex) {
        if (!status.equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("commandBack"))) {
            if (InputChecker.inputInArray(status, tasksStates)>0) {
                Task task = taskDAO.findById(findByOwner(owner)
                        .get(taskIndex-1)
                        .getId())
                        .orElseThrow();
                task.setStatus(status);
                taskDAO.save(task);
                return 1;
            }
            return -1;
        }
        return 0;
    }
}
