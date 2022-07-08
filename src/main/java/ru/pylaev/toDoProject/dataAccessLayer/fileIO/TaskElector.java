package ru.pylaev.toDoProject.dataAccessLayer.fileIO;

import ru.pylaev.toDoProject.dataAccessLayer.Task;

@FunctionalInterface
public interface TaskElector<T> {
    boolean elect (Task task, T o1);
}
