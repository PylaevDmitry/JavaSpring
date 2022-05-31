package ru.pylaev.toDoProject.pl.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.Task;
import ru.pylaev.toDoProject.dal.TaskRepository;
import ru.pylaev.toDoProject.pl.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ControllerHandlerTest {

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    ControllerHandler controllerHandler;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Task task1 = new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT");
        Task task2 = new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT");
        Task task3 = new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE");

        tasks.clear();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        Mockito.when(taskRepository.findByOwner("user")).thenReturn(tasks);
        Mockito.when(taskRepository.findById(3L)).thenReturn(Optional.of(task1));
        Mockito.when(taskRepository.findById(11L)).thenReturn(Optional.of(task2));
        Mockito.when(taskRepository.findById(14L)).thenReturn(Optional.of(task3));

    }

    @Test
    void processOwnerIsOk () {
        View view = new View();

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasksAsList(tasks);

        controllerHandler.processView("user", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processOwnerInvalidSymbol () {
        View view = new View();

        View expectedView = new View();

        controllerHandler.processView( "???", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processOwnerNull () {
        View view = new View();

        View expectedView = new View();

        controllerHandler.processView( null, view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberOk () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(1);
        expectedView.setMessage(askStatus);
        expectedView.setTasksAsList(tasks);

        controllerHandler.processView( "1", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberOutRange () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasksAsList(tasks);

        controllerHandler.processView( "10", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberNull () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);

        controllerHandler.processView( null, view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNewOk () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNew);

        Task task = new Task("33", "user", "note4", "Wed Mar 25 16:01", "WAIT");
        tasks.add(task);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasksAsList(tasks);

        controllerHandler.processView( "note4", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskStatusDone () {
        View view = new View();
        view.setOwner("user");
        view.setTaskIndex(1);
        view.setMessage(askStatus);
        view.setTasksAsList(tasks);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(1);
        expectedView.setMessage(askNumber);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.set(0, new Task("3", "user", "note3", "Wed Mar 25 16:01", "DONE"));
        expectedView.setTasksAsList(expectList);

        controllerHandler.processView( "DONE", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskStatusArch () {
        View view = new View();
        view.setOwner("user");
        view.setTaskIndex(3);
        view.setMessage(askStatus);
        view.setTasksAsList(tasks);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(3);
        expectedView.setMessage(askNumber);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.remove(2);
        expectedView.setTasksAsList(expectList);

        controllerHandler.processView("ARCH", view);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskStatusInvalid () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasksAsList(tasks);

        controllerHandler.processView( "arc", view);

        Assertions.assertEquals(view, expectedView);
    }

}