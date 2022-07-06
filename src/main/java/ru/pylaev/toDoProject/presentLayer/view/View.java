package ru.pylaev.toDoProject.presentLayer.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@Scope("prototype")
public class View {
    private Messages message = Messages.askOwner;
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

    public Messages getMessage ( ) {
        return message;
    }

    public void setMessage (Messages message) {
        this.message = message;
    }

    public String[] getArrTasks ( ) {
        return arrTasks;
    }

    public void setTasksAsList(List<Task> list) {
        arrTasks = new String[list.size()];
        IntStream.range(0, list.size()).forEach(i -> this.getArrTasks()[i] = i + 1 + " " + list.get(i));
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (taskIndex != view.taskIndex) return false;
        if (!Objects.equals(message, view.message)) return false;
        if (!Arrays.deepEquals(arrTasks, view.arrTasks)) return false;
        return Objects.equals(owner, view.owner);
    }

    @Override
    public int hashCode ( ) {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(arrTasks);
        result = 31 * result + taskIndex;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
