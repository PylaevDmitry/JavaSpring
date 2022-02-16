package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.bll.ToDoHandler;
import ru.pylaev.toDoProject.dal.dao.DbTasksDao;
import ru.pylaev.toDoProject.dal.dao.FileTasksDao;
import ru.pylaev.toDoProject.pl.presenter.ConsoleUserInterface;
import ru.pylaev.util.CustomProperties;

@SpringBootApplication
public class ToDoMain {

    public static final CustomProperties CUSTOM_PROPERTIES = new CustomProperties("customConfig");

    public static void main (String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(ToDoMain.class).headless(false).run(args);

        ConsoleUserInterface consoleUserInterface = applicationContext.getBean("consoleUserInterface", ConsoleUserInterface.class);

//        TelegramBotUserInterface telegramBotUserInterface = applicationContext.getBean("telegramBotUserInterface", TelegramBotUserInterface.class);
//        WindowUserInterface windowUserInterface = applicationContext.getBean("windowUserInterface", WindowUserInterface.class);

        FileTasksDao fileTasksDao = applicationContext.getBean("fileTasksDao", FileTasksDao.class);
        DbTasksDao dbTasksDao = applicationContext.getBean("dbTasksDao", DbTasksDao.class);

        ToDoHandler toDoHandler1 = new ToDoHandler(consoleUserInterface, dbTasksDao);
        Thread tread1 = new Thread(toDoHandler1);
        tread1.start();

//        ToDoHandler toDoHandler2 = new ToDoHandler(telegramBotUserInterface, fileTasksDao);
//        Thread tread2 = new Thread(toDoHandler2);
//        tread2.start();
//
//        ToDoHandler toDoHandler3 = new ToDoHandler(windowUserInterface, fileTasksDao);
//        toDoHandler3.run();

    }
}

