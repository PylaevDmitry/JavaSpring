package ru.pylaev.toDoProject.dataAccessLayer.fileIO;

import ru.pylaev.toDoProject.dataAccessLayer.Task;

class ElectorByOwner implements TaskElector<String> {
    public boolean elect(Task task, String owner) {return task.getOwner().equals(owner);}
}
