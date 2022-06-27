package ru.pylaev.toDoProject.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String owner;
    private String text;
    private String date;
    private String status;

    public Task ( ) {
    }

    public Task (String owner, String text, Date date, String status) {
        this.id = 0;
        this.owner = owner;
        this.text = text;
        this.date = String.valueOf(date).substring(0,16);
        this.status = status;
    }

    public Task (String id, String owner, String text, String date, String status) {
        this.id = Integer.parseInt(id);
        this.owner = owner;
        this.text = text;
        this.date = date.substring(0,16);
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setStatus (String status) { this.status = status; }

    public long getId ( ) { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus ( ) {
        return status;
    }

    @Override
    public String toString () { return  text + ' ' + date + ' ' + status; }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (!Objects.equals(owner, task.owner)) return false;
        if (!Objects.equals(text, task.text)) return false;
        if (!Objects.equals(date, task.date)) return false;
        return Objects.equals(status, task.status);
    }

    @Override
    public int hashCode ( ) {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}

