package ru.pylaev.toDoProject.dal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String owner = null;
    private String text = null;
    private String date = null;
    private String status;

    public Task ( ) {
    }

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

