package ru.pylaev.toDoProject.abstractions;

import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.models.Task;

@Component
public interface IStorage {
    Task[] getAll ();
    void add (Task data);
    void setStatus (long id, String status);
    void setOwner (String owner);
    String getOwner();
}
