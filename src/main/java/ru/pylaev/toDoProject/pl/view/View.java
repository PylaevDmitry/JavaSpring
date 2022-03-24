package ru.pylaev.toDoProject.pl.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@Scope("prototype")
public class View {
    private String message = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private String[] arrTasks;
    private int taskIndex;
    private String owner;

    public int getTaskIndex ( ) {
        return taskIndex;
    }

    public void setTaskIndex (int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public String getOwner ( ) {
        return owner;
    }

    public void setOwner (String owner) {
        this.owner = owner;
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

    public void setTasks (List<Task> finalList) {
        arrTasks = new String[finalList.size()];
        IntStream.range(0, finalList.size())
                .forEach(i -> this.getArrTasks()[i] = i + 1 + " " + finalList.get(i));
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (!Objects.equals(message, view.message)) return false;
        return Arrays.equals(arrTasks, view.arrTasks);
    }

    @Override
    public int hashCode ( ) {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(arrTasks);
        return result;
    }
}
