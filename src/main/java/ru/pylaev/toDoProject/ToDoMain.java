package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.presentLayer.runUi.ConsoleUserInterface;
import ru.pylaev.toDoProject.presentLayer.runUi.TelegramUserInterface;
import ru.pylaev.toDoProject.presentLayer.runUi.RunUI;
import ru.pylaev.toDoProject.presentLayer.runUi.WindowUserInterface;
import ru.pylaev.util.CustomProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ToDoMain {
    public static final CustomProperties CUSTOM_PROPERTIES = new CustomProperties("customConfig");
    public static ApplicationContext applicationContext;

    public static void main (String[] args) {
        applicationContext = new SpringApplicationBuilder(ToDoMain.class).headless(false).run();

        ConsoleUserInterface consoleUserInterface = applicationContext.getBean("consoleUserInterface", ConsoleUserInterface.class);
        TelegramUserInterface telegramUserInterface = applicationContext.getBean("telegramUserInterface", TelegramUserInterface.class);
        WindowUserInterface windowUserInterface = applicationContext.getBean("windowUserInterface", WindowUserInterface.class);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new UserInterfaceRunner(consoleUserInterface));
        executorService.execute(new UserInterfaceRunner(telegramUserInterface));
        executorService.execute(new UserInterfaceRunner(windowUserInterface));
    }
}

record UserInterfaceRunner(RunUI runUI) implements Runnable {
    @Override
    public void run() {
        runUI.showStartView();
        while (true) {
            runUI.processUserInput();
        }
    }
}

