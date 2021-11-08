package ru.pylaev.toDoProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.businessLayer.ToDoHandler;
import util.CustomProperties;

import java.util.Map;

@SpringBootApplication
public class ToDoMain {
    public static final Map<String, String> environmentVars = System.getenv();
    public static final CustomProperties properties = new CustomProperties("ru.pylaev.toDoProject.customConfig");

    public static void main (String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(ToDoMain.class, args);

        ToDoHandler toDoHandler = applicationContext.getBean("toDoHandler", ToDoHandler.class);
        Thread tread = new Thread(toDoHandler);
        tread.start();

    }
}

