package ru.pylaev.toDoProject.presentLayer;

import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class BaseUI {
    protected View view = new View();
    protected State state;

    public BaseUI(State state) {
        this.state = state;
    }
}
