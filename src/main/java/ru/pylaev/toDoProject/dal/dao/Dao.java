package ru.pylaev.toDoProject.dal.dao;

import ru.pylaev.toDoProject.dal.entity.Task;

public interface Dao {
    Task[] getAll ();
    Task[] get (long id);
    long add (Task data);
    void setStatus (long id, String status);
    void setOwner (String owner);
}
