package ru.pylaev.toDoProject.presentLayer.runUi;

import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.presentLayer.BaseUI;

public abstract class RunUI extends BaseUI {
    public RunUI(State state) {
        super(state);
    }

    public abstract void showStartView();

    public abstract void processUserInput();
}
