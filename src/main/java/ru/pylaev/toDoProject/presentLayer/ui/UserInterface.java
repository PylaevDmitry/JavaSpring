package ru.pylaev.toDoProject.presentLayer.ui;

import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class UserInterface implements Runnable {
    protected View view;
    protected final UserInputService userInputService;

    public UserInterface(View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
    }

    public abstract void run();
}
