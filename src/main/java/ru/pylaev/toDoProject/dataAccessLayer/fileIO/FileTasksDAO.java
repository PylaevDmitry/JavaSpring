package ru.pylaev.toDoProject.dataAccessLayer.fileIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ClassCanBeRecord")
@Component
//@Primary
public class FileTasksDAO implements DAO {
    private final String path;

    public FileTasksDAO(@Value("${filePath}") String path) {
        this.path = path;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    @Override
    public synchronized List<Task> findByOwner (String owner) {
        return findTasks(owner, (task, o1) -> task.getOwner().equals(o1));
    }

    @Override
    public synchronized Optional<Task> findById (long id) {
        return Optional.ofNullable(findTasks(id, (task, o1) -> task.getId()==o1).get(0));
    }

    @Override
    public synchronized void save (Task task) {
        var tasks = findTasks("addAll", (t1, o1) -> true);
        boolean taskIsFound = false;
        long lastIndex = 0;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (var taskItem : tasks) {
                if (taskItem.getId() == task.getId()) {
                    taskItem.setStatus(task.getStatus());
                    taskIsFound = true;
                }
                lastIndex++;
                objectOutputStream.writeObject(taskItem);
            }
            if (taskIsFound) return;
            task.setId(lastIndex+1);
            objectOutputStream.writeObject(task);
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    private <T> List<Task> findTasks(T target, TaskElector<T> taskElector) {
        var result = new ArrayList<Task>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            while (true) {
                var task = (Task) objectInputStream.readObject();
                if (taskElector.elect(task, target)) {
                    result.add(task);
                }
            }
        } catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
        return result;
    }
}
