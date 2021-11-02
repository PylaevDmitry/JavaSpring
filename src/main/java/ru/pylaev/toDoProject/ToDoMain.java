package ru.pylaev.toDoProject;

import ru.pylaev.toDoProject.businessLayer.ToDoHandler;
import ru.pylaev.toDoProject.dal.DbTasksDao;
import ru.pylaev.toDoProject.dal.FileTasksDao;
import ru.pylaev.toDoProject.userInterfaces.ConsoleUserInterface;
import ru.pylaev.toDoProject.userInterfaces.WindowUserInterface;
import util.CustomProperties;

import java.util.Map;

public class ToDoMain {

    public static final Map<String, String> environmentVars = System.getenv();
    public static final CustomProperties properties = new CustomProperties("ru.pylaev.toDoProject.customConfig");

    public static void main (String[] args) {

        ToDoProjectApplication.main(new String[]{});

//        var windowUserInterface = new WindowUserInterface();
//        var telegramBotUserInterface = new TelegramBotUserInterface(environmentVars.get("botToken"));
//
//        var dbTasksDao = new DbTasksDao(environmentVars.get("dbUserName"), environmentVars.get("dbUserPass"));
//
//        var consoleUserInterface = new ConsoleUserInterface();
//        var fileTasksDao = new FileTasksDao(environmentVars.get("filePath"));
//        ToDoHandler toDoHandler1 = new ToDoHandler(consoleUserInterface, dbTasksDao);
//        Thread t1 = new Thread(toDoHandler1);
//        t1.start();
//
//        ToDoHandler toDoHandler = new ToDoHandler(windowUserInterface, fileTasksDao);
//        toDoHandler.run();

    }
}

