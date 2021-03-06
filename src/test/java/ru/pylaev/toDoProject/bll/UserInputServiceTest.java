package ru.pylaev.toDoProject.bll;

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
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.dal.repo.TaskRepository;
import ru.pylaev.toDoProject.pl.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class UserInputServiceTest {

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");


    private TaskRepository taskRepository;


    UserInputService userInputService;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Task task1 = new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT");
        Task task2 = new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE");
        Task task3 = new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT");

        tasks.clear();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        taskRepository = Mockito.mock(TaskRepository.class);

        Mockito.when(taskRepository.findByOwner("user")).thenReturn(tasks);
        Mockito.when(taskRepository.findById(11L)).thenReturn(Optional.of(task1));
        Mockito.when(taskRepository.findById(14L)).thenReturn(Optional.of(task2));
        Mockito.when(taskRepository.findById(3L)).thenReturn(Optional.of(task3));

        userInputService = new UserInputService(taskRepository);
    }

    @Test
    void processOwnerIsOk () {
        View view = new View();

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        View resultView = userInputService.howToServe(view, "user");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processOwnerInvalidSymbol () {
        View view = new View();

        View expectedView = new View();

        View resultView = userInputService.howToServe(view, "???");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processOwnerNull () {
        View view = new View();

        View expectedView = new View();

        View resultView = userInputService.howToServe(view, null);

        Assertions.assertEquals(resultView, expectedView);
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
        expectedView.setTasks(tasks);

        View resultView = userInputService.howToServe(view, "1");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processAskNumberOutRange () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        View resultView = userInputService.howToServe(view, "10");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processAskNumberNull () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);

        View resultView = userInputService.howToServe(view, null);

        Assertions.assertEquals(resultView, expectedView);
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
        expectedView.setTasks(tasks);

        View resultView = userInputService.howToServe(view, "note4");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processAskStatusDone () {
        View view = new View();
        view.setOwner("user");
        view.setTaskIndex(3);
        view.setMessage(askStatus);
        view.setTasks(tasks);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(3);
        expectedView.setMessage(askNumber);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.set(2, new Task("3", "user", "note3", "Wed Mar 25 16:01", "DONE"));
        expectedView.setTasks(expectList);

        View resultView = userInputService.howToServe(view, "DONE");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processAskStatusArch () {
        View view = new View();
        view.setOwner("user");
        view.setTaskIndex(3);
        view.setMessage(askStatus);
        view.setTasks(tasks);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(3);
        expectedView.setMessage(askNumber);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.remove(2);
        expectedView.setTasks(expectList);

        View resultView = userInputService.howToServe(view, "ARCH");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void processAskStatusInvalid () {
        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        View resultView = userInputService.howToServe(view, "arc");

        Assertions.assertEquals(resultView, expectedView);
    }

    @Test
    void setOwner_validData_changeOwner () {

        View resultView = userInputService.setOwner(view, "arc");
    }

}