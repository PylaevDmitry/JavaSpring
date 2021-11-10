package ru.pylaev.toDoProject.models;

import ru.pylaev.toDoProject.ToDoMain;

public class View {
    private String message = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private String[] arrTasks;

    public View (String message, String[] arrTasks) {
        this.message = message;
        this.arrTasks = arrTasks;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String[] getArrTasks ( ) {
        return arrTasks;
    }

    public void setArrTasks (String[] arrTasks) {
        this.arrTasks = arrTasks;
    }
}
