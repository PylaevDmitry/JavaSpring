package ru.pylaev.toDoProject.presentLayer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.view.View;
import ru.pylaev.util.HeadlessSpringBootContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = HeadlessSpringBootContextLoader.class)
class ViewHandlerTest {
    @MockBean
    private DAO tasksDAO;

    @Autowired
    TaskRepository taskRepository;

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

        Mockito.when(tasksDAO.findByOwner("user")).thenReturn(tasks);
        Mockito.when(tasksDAO.findById(3L)).thenReturn(Optional.of(task1));
        Mockito.when(tasksDAO.findById(11L)).thenReturn(Optional.of(task2));
        Mockito.when(tasksDAO.findById(14L)).thenReturn(Optional.of(task3));
    }

//    @Test
//    void getCurrentIndexIsOk() {
//        assertEquals(validateIndex("3", tasks.size()), 3);
//    }
//
//    @Test
//    void getCurrentIndexIsNull() {
//        assertEquals(validateIndex("2", 0), 0);
//    }
//
//    @Test
//    void getCurrentIndexIsRejected() {
//        assertEquals(validateIndex("4", tasks.size()), -1);
//    }

    @Test
    void processOwnerIsOk () {
        View view = new View();

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTasks(tasks);

        ViewHandler.processUserInput("user", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processOwnerInvalidSymbol () {
        View view = new View();

        View expectedView = new View();

        ViewHandler.processUserInput( "???", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processOwnerNull () {
        View view = new View();

        View expectedView = new View();

        ViewHandler.processUserInput( null, view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberOk () {
        View view = new View();
        view.setOwner("user");

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(1);
        expectedView.setMessage(Messages.askStatus);
        expectedView.setTasks(tasks);

        ViewHandler.processUserInput( "1", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberOutRange () {
        View view = new View();
        view.setOwner("user");

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTasks(tasks);

        ViewHandler.processUserInput( "10", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberNull () {
        View view = new View();
        view.setOwner("user");

        View expectedView = new View();
        expectedView.setOwner("user");

        ViewHandler.processUserInput( null, view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNewOk () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(Messages.askNew);

        Task task = new Task("33", "user", "note4", "Wed Mar 25 16:01", "WAIT");
        tasks.add(task);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTasks(tasks);

        ViewHandler.processUserInput( "note4", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskStatusDone () {
        View view = new View();
        view.setOwner("user");
        view.setTaskIndex(1);
        view.setMessage(Messages.askStatus);
        view.setTasks(tasks);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(1);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.set(0, new Task("3", "user", "note3", "Wed Mar 25 16:01", "DONE"));
        expectedView.setTasks(expectList);

        ViewHandler.processUserInput( "DONE", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskStatusArch () {
        View view = new View();
        view.setOwner("user");
        view.setTaskIndex(3);
        view.setMessage(Messages.askStatus);
        view.setTasks(tasks);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(3);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.remove(2);
        expectedView.setTasks(expectList);

        ViewHandler.processUserInput("ARCH", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskStatusInvalid () {
        View view = new View();
        view.setOwner("user");

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTasks(tasks);

        ViewHandler.processUserInput( "arc", view, taskRepository);

        Assertions.assertEquals(view, expectedView);
    }
}