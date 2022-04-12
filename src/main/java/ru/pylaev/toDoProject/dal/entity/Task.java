package ru.pylaev.toDoProject.dal.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

// Anemic or Rich
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String owner;
    public String text;
    public String date;
    public String status;

    @Override
    public String toString () { return  text + ' ' + date + ' ' + status; }
}