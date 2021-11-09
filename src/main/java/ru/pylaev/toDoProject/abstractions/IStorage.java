package ru.pylaev.toDoProject.abstractions;

import ru.pylaev.toDoProject.models.Task;

public interface IStorage {
    Task[] getAll ();
    void add (Task data);
    void setStatus (long id, String status);
    void setOwner (String owner);
}
