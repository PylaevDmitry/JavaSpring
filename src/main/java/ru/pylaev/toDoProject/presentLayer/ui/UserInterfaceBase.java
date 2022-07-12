package ru.pylaev.toDoProject.presentLayer.ui;

import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class UserInterfaceBase {
    protected View view;
    protected final TaskRepository taskRepository;

    public UserInterfaceBase(View view, TaskRepository taskRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
    }

    public abstract void showStartView();

    public abstract void processUserInput();
}
