package ru.pylaev.toDoProject.presentLayer.ui;

import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class UserInterfaceBase {
    protected View view = new View();
    protected State state;

    public UserInterfaceBase(State state) {
        this.state = state;
    }

    public abstract void showStartView();

    public abstract void processUserInput();
}
