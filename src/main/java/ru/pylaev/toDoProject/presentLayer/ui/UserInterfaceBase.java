package ru.pylaev.toDoProject.presentLayer.ui;

import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class UserInterfaceBase {
    protected View view;
    protected final UserInputService userInputService;

    public UserInterfaceBase(View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
    }

    public abstract void showStartView();

    public abstract void processUserInput();
}
