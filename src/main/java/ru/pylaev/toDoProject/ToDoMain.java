package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.presentLayer.ui.WindowUserInterface;
import ru.pylaev.toDoProject.presentLayer.ui.ConsoleUserInterface;
import ru.pylaev.util.CustomProperties;

@SpringBootApplication
public class ToDoMain {
    public static final CustomProperties CUSTOM_PROPERTIES = new CustomProperties("customConfig");

    public static void main (String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(ToDoMain.class).headless(false).run(args);

        ConsoleUserInterface consoleUserInterface = applicationContext.getBean("consoleUserInterface", ConsoleUserInterface.class);
//        TelegramBotUserInterface telegramBotUserInterface = applicationContext.getBean("telegramBotUserInterface", TelegramBotUserInterface.class);
//        WindowUserInterface windowUserInterface = applicationContext.getBean("windowUserInterface", WindowUserInterface.class);

        Thread thread1 = new Thread(consoleUserInterface);
        thread1.start();

//        Thread thread2 = new Thread(telegramBotUserInterface);
//        thread2.start();
//
//        Thread thread3 = new Thread(windowUserInterface);
//        thread3.start();

    }
}

