package ru.pylaev.toDoProject.dal.fileIO;

import ru.pylaev.toDoProject.dal.Task;

public interface TaskElector<T> {
    boolean elect (Task task, T o1);
}
