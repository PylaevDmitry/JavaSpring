package ru.pylaev.toDoProject.dataAccessLayer.fileIO;

import ru.pylaev.toDoProject.dataAccessLayer.Task;

public interface TaskElector<T> {
    boolean elect (Task task, T o1);
}
