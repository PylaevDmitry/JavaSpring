package ru.pylaev.toDoProject.dal.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class TaskRich implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String _status;

    public class TaskRich(String status){
        if(status)

        _status = status;
    }


}
