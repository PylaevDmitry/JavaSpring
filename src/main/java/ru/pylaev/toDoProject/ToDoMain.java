package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.util.CustomProperties;

@SpringBootApplication
public class ToDoMain {

    public static final CustomProperties CUSTOM_PROPERTIES = new CustomProperties("customConfig");

    public static void main (String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(ToDoMain.class).headless(false).run(args);
    }
}

