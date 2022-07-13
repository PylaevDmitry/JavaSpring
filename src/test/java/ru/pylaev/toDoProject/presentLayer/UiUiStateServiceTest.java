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
import ru.pylaev.toDoProject.businessLogicLayer.UiStateService;
import ru.pylaev.toDoProject.businessLogicLayer.Step;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.businessLogicLayer.UiState;
import ru.pylaev.util.HeadlessSpringBootContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = HeadlessSpringBootContextLoader.class)
class UiUiStateServiceTest {
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
        UiState uiState = new UiState();

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput("user", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processOwnerInvalidSymbol () {
        UiState uiState = new UiState();

        UiState expectedUiState = new UiState();

        UiStateService.processUserInput( "???", uiState);

        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processOwnerNull () {
        UiState uiState = new UiState();

        UiState expectedUiState = new UiState();

        UiStateService.processUserInput( null, uiState);

        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskNumberOk () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        expectedUiState.setCurrentTaskIndex(1);
        expectedUiState.setStep(Step.askStatus);
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput( "1", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskNumberOutRange () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput( "10", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskNumberNull () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");

        UiStateService.processUserInput( null, uiState);

        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskNewOk () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");
        uiState.setStep(Step.askNew);

        Task task = new Task("33", "user", "note4", "Wed Mar 25 16:01", "WAIT");
        tasks.add(task);

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput( "note4", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskStatusDone () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");
        uiState.setCurrentTaskIndex(1);
        uiState.setStep(Step.askStatus);

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        expectedUiState.setCurrentTaskIndex(1);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.set(0, new Task("3", "user", "note3", "Wed Mar 25 16:01", "DONE"));
        String[] expectedTasks = expectList.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput( "DONE", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskStatusArch () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");
        uiState.setCurrentTaskIndex(3);
        uiState.setStep(Step.askStatus);

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        expectedUiState.setCurrentTaskIndex(3);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.remove(2);
        String[] expectedTasks = expectList.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput("ARCH", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }

    @Test
    void processAskStatusInvalid () {
        UiState uiState = new UiState();
        uiState.setCorrectOwner("user");

        UiState expectedUiState = new UiState();
        expectedUiState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = UiStateService.processUserInput( "arc", uiState);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedUiState, uiState);
    }
}