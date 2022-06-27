package ru.pylaev.toDoProject.dal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FileTasksDAO implements DAO {
    private final String path;
    private long lastIndex = 0;

    public FileTasksDAO(@Value("${filePath}") String path) {
        this.path = path;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    @Override
    public List<Task> findByOwner (String owner) {
        var result = new ArrayList<Task>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            while (true) {
                var task = (Task) objectInputStream.readObject();
                if (task.getOwner().equals(owner)) {
                    result.add(task);
                }
            }
        } catch (EOFException e) {
            lastIndex = result.size();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
        return result;
    }

    @Override
    public Optional<Task> findById (long id) {
        Task result = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            while (true) {
                var task = (Task) objectInputStream.readObject();
                if (task.getId()==id) {
                    result=task;
                }
            }
        } catch (EOFException ignored) {
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
        return Optional.ofNullable(result);
    }

    @Override
    public synchronized void save (Task task) {
        var tasks = findByOwner(task.getOwner());
        boolean taskIsFound = false;
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (var taskItem : tasks) {
                if (taskItem.getId() == task.getId()) {
                    taskItem.setStatus(task.getStatus());
                    taskIsFound = true;
                }
                objectOutputStream.writeObject(taskItem);
            }
            if (!taskIsFound) add (task);
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    private synchronized void add (Task task) {
        task.setId(lastIndex+1);
        var tasks = findByOwner(task.getOwner());
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (Task item:tasks) {objectOutputStream.writeObject(item);}
            objectOutputStream.writeObject(task);
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

}
