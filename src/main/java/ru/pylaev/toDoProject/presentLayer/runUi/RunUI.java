package ru.pylaev.toDoProject.presentLayer.runUi;

import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.presentLayer.BaseUI;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class RunUI extends BaseUI {
    public RunUI(State state, View view) {
        super(state, view);
    }

    public abstract void showStartView();

    public abstract void processUserInput();
}
