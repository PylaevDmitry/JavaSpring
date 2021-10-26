package ru.pylaev.toDoProject.models;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private long id;
    private final String owner;
    private final String text;
    private final String date;
    private String status;

    public Task (String owner, String text, Date date, String status) {
        this.id = 0;
        this.owner = owner;
        this.text = text;
        this.date = String.valueOf(date);
        this.status = status;
    }

    public Task (String id, String owner, String text, String date, String status) {
        this.id = Integer.parseInt(id);
        this.owner = owner;
        this.text = text;
        this.date = String.valueOf(date);
        this.status = status;
    }

    public void setId (long id) { this.id = id; }

    public void setStatus (String status) { this.status = status; }

    public long getId ( ) { return id; }

    public String getOwner ( ) { return owner;}

    public String getText ( ) {
        return text;
    }

    public String getDate ( ) {
        return date;
    }

    public String getStatus ( ) {
        return status;
    }

    @Override
    public String toString () { return  text + ' ' + date + ' ' + status; }

}

