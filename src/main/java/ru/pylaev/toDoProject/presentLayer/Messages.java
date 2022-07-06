package ru.pylaev.toDoProject.presentLayer;

import ru.pylaev.toDoProject.ToDoMain;

public enum Messages {
    askOwner(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner")),
    askNumber(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber")),
    askNew(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew")),
    askStatus(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus"));

    private final String content;

    Messages(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
