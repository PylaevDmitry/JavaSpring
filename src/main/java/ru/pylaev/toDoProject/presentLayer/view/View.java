package ru.pylaev.toDoProject.presentLayer.view;

import ru.pylaev.toDoProject.businessLogicLayer.Step;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return Objects.equals(owner, view.owner) && Arrays.equals(tasks, view.tasks) && Objects.equals(message, view.message);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(owner, message);
        result = 31 * result + Arrays.hashCode(tasks);
        return result;
    }
}
