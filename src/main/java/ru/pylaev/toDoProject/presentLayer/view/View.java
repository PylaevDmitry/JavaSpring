package ru.pylaev.toDoProject.presentLayer.view;

import ru.pylaev.toDoProject.businessLogicLayer.Step;

public class View {
    private String owner;
    private String[] tasks;
    private String message = Step.askOwner.toString();

    public View() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String[] getTasks() {
        return tasks;
    }

    public void setTasks(String[] tasks) {
        this.tasks = tasks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
