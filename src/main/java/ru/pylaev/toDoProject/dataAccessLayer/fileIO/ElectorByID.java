package ru.pylaev.toDoProject.dataAccessLayer.fileIO;

import ru.pylaev.toDoProject.dataAccessLayer.Task;

class ElectorByID implements TaskElector<Long> {
    public boolean elect(Task task, Long id) {return task.getId()==id;}
}
