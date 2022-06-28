package ru.pylaev.toDoProject.dal.fileIO;

import ru.pylaev.toDoProject.dal.Task;

class ElectorByOwner implements TaskElector<String> {
    public boolean elect(Task task, String owner) {return task.getOwner().equals(owner);}
}
