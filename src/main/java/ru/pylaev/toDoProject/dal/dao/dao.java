package ru.pylaev.toDoProject.dal.dao;

import ru.pylaev.toDoProject.dal.entity.Task;

public interface dao {
    Task[] getAll ();
    void add (Task data);
    void setStatus (long id, String status);
    void setOwner (String owner);
}
