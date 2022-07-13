package ru.pylaev.toDoProject.businessLogicLayer;

import ru.pylaev.toDoProject.ToDoMain;

public enum Step {
    askOwner(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner")),
    askNumber(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber")),
    askNew(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew")),
    askStatus(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus"));

    private final String content;

    Step(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
