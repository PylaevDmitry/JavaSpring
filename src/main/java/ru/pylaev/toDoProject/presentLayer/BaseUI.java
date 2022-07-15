package ru.pylaev.toDoProject.presentLayer;

import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class BaseUI {
    protected View view;
    protected State state;

    public BaseUI(State state, View view) {
        this.state = state;
        this.view = view;
    }
}
