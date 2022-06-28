package ru.pylaev.toDoProject.dal.fileIO;

import ru.pylaev.toDoProject.dal.Task;

class ElectorByID implements TaskElector<Long> {
    public boolean elect(Task task, Long id) {return task.getId()==id;}
}
