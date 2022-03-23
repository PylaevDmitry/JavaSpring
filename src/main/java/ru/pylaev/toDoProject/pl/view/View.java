package ru.pylaev.toDoProject.pl.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.util.List;
import java.util.stream.IntStream;

@Component
@Scope("prototype")
public class View {
    private String message = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private String[] arrTasks;

    public String getMessage ( ) {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String[] getArrTasks ( ) {
        return arrTasks;
    }

    public void setTasks (List<Task> finalList) {
        arrTasks = new String[finalList.size()];
        IntStream.range(0, finalList.size())
                .forEach(i -> this.getArrTasks()[i] = i + 1 + " " + finalList.get(i));
    }
}
