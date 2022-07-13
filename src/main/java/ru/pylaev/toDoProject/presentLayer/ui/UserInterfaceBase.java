package ru.pylaev.toDoProject.presentLayer.ui;

import ru.pylaev.toDoProject.businessLogicLayer.UiState;
import ru.pylaev.toDoProject.presentLayer.view.View;

public abstract class UserInterfaceBase {
    protected View view;
    protected UiState uiState;

    public UserInterfaceBase(UiState uiState) {
        this.uiState = uiState;
    }

    public abstract void showStartView();

    public abstract void processUserInput();
}
