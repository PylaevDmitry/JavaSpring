package ru.pylaev.toDoProject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import ru.pylaev.toDoProject.bll.service.ToDoService;
import ru.pylaev.toDoProject.dal.dao.DbTasksDao;
import ru.pylaev.toDoProject.dal.dao.FileTasksDao;
import ru.pylaev.toDoProject.pl.presenter.ConsoleUserInterface;
import ru.pylaev.util.CustomProperties;

@SpringBootApplication
public class ToDoMain {

    public static final CustomProperties CUSTOM_PROPERTIES = new CustomProperties("customConfig");

    public static void main (String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(ToDoMain.class).headless(false).run(args);

//        WindowUserInterface windowUserInterface = applicationContext.getBean("windowUserInterface", WindowUserInterface.class);
        ConsoleUserInterface consoleUserInterface = applicationContext.getBean("consoleUserInterface", ConsoleUserInterface.class);
//        TelegramBotUserInterface telegramBotUserInterface = applicationContext.getBean("telegramBotUserInterface", TelegramBotUserInterface.class);

        FileTasksDao fileTasksDao = applicationContext.getBean("fileTasksDao", FileTasksDao.class);
        DbTasksDao dbTasksDao = applicationContext.getBean("dbTasksDao", DbTasksDao.class);

        ToDoService toDoService1 = new ToDoService(consoleUserInterface, dbTasksDao);
        Thread tread1 = new Thread(toDoService1);
        tread1.start();

//        ToDoService toDoService2 = new ToDoService(telegramBotUserInterface, fileTasksDao);
//        Thread tread2 = new Thread(toDoService2);
//        tread2.start();

//        ToDoService toDoService3 = new ToDoService(windowUserInterface, fileTasksDao);
//        toDoService3.run();

    }
}

