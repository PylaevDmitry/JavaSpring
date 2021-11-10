package ru.pylaev.toDoProject.models;

import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;

@Component
public class View {
    private String message = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private String[] arrTasks;

    public View ( ) {
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
