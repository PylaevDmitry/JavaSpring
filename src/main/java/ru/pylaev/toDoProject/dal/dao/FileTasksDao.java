package ru.pylaev.toDoProject.dal.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Component
public class FileTasksDao implements Dao {
    private final String path;
    private String owner;
    private long lastIndex = 0;

    @Override
    public void setOwner (String owner) {
        this.owner = owner;
    }

    public FileTasksDao (@Value("${filePath}") String path) {
        this.path = path;
        try {
            if (!Files.exists(Paths.get(path))) Files.createFile(Paths.get(path));
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    @Override
    public Task[] getAll () {
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
        return (result.size()==0)?new Task[0]:result.toArray(Task[]::new);
    }

    @Override
    public synchronized void add (Task task) {
        task.setId(lastIndex+1);
        var tasks = getAll();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (Task item:tasks) {objectOutputStream.writeObject(item);}
            objectOutputStream.writeObject(task);
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    @Override
    public synchronized void setStatus (long id, String status) {
        var tasks = getAll();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
            for (var task : tasks) {
                if (task.getId() == id) task.setStatus(status);
                objectOutputStream.writeObject(task);
            }
        } catch (IOException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }
}
