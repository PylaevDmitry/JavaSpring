package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.presentLayer.ui.ConsoleUserInterface;
import ru.pylaev.toDoProject.presentLayer.ui.TelegramBotUserInterface;
import ru.pylaev.toDoProject.presentLayer.ui.UserInterfaceBase;
import ru.pylaev.toDoProject.presentLayer.ui.WindowUserInterface;
import ru.pylaev.util.CustomProperties;

@SpringBootApplication
public class ToDoMain {
    public static final CustomProperties CUSTOM_PROPERTIES = new CustomProperties("customConfig");

    public static void main (String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(ToDoMain.class).headless(false).run(args);

        ConsoleUserInterface consoleUserInterface = applicationContext.getBean("consoleUserInterface", ConsoleUserInterface.class);
        TelegramBotUserInterface telegramBotUserInterface = applicationContext.getBean("telegramBotUserInterface", TelegramBotUserInterface.class);
        WindowUserInterface windowUserInterface = applicationContext.getBean("windowUserInterface", WindowUserInterface.class);

        new Thread(new Runner(consoleUserInterface)).start();
        new Thread(new Runner(telegramBotUserInterface)).start();
        new Thread(new Runner(windowUserInterface)).start();
    }
}

record Runner (UserInterfaceBase userInterfaceBase) implements Runnable {
    @Override
    public void run() {
        userInterfaceBase.showStartView();
        while (true) {
            userInterfaceBase.processUserActions();
        }
    }
}

