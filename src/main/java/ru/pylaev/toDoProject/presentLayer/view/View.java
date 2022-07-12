package ru.pylaev.toDoProject.presentLayer.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.presentLayer.Messages;

import java.util.Objects;

@Component
@Scope("prototype")
public class View {
    private Messages message = Messages.askOwner;

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
        if (Objects.nonNull(owner)) {
            this.owner = owner;
            message = Messages.askNumber;
        }
    }

    public Messages getMessage ( ) {
        return message;
    }

    public void setMessage (Messages message) {
        this.message = message;
    }

    public void reset() {
        this.message = Messages.askOwner;
        this.owner = null;
        this.taskIndex = 0;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (taskIndex != view.taskIndex) return false;
        if (!Objects.equals(message, view.message)) return false;
        return Objects.equals(owner, view.owner);
    }

    @Override
    public int hashCode ( ) {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + taskIndex;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
