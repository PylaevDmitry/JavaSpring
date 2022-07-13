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
import ru.pylaev.toDoProject.businessLogicLayer.StateService;
import ru.pylaev.toDoProject.businessLogicLayer.Step;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.util.HeadlessSpringBootContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = HeadlessSpringBootContextLoader.class)
class StateServiceTest {
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
        State state = new State();

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput("user", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processOwnerInvalidSymbol () {
        State state = new State();

        State expectedState = new State();

        StateService.processUserInput( "???", state);

        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processOwnerNull () {
        State state = new State();

        State expectedState = new State();

        StateService.processUserInput( null, state);

        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskNumberOk () {
        State state = new State();
        state.setCorrectOwner("user");

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        expectedState.setCurrentTaskIndex(1);
        expectedState.setStep(Step.askStatus);
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput( "1", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskNumberOutRange () {
        State state = new State();
        state.setCorrectOwner("user");

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput( "10", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskNumberNull () {
        State state = new State();
        state.setCorrectOwner("user");

        State expectedState = new State();
        expectedState.setCorrectOwner("user");

        StateService.processUserInput( null, state);

        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskNewOk () {
        State state = new State();
        state.setCorrectOwner("user");
        state.setStep(Step.askNew);

        Task task = new Task("33", "user", "note4", "Wed Mar 25 16:01", "WAIT");
        tasks.add(task);

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput( "note4", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskStatusDone () {
        State state = new State();
        state.setCorrectOwner("user");
        state.setCurrentTaskIndex(1);
        state.setStep(Step.askStatus);

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        expectedState.setCurrentTaskIndex(1);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.set(0, new Task("3", "user", "note3", "Wed Mar 25 16:01", "DONE"));
        String[] expectedTasks = expectList.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput( "DONE", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskStatusArch () {
        State state = new State();
        state.setCorrectOwner("user");
        state.setCurrentTaskIndex(3);
        state.setStep(Step.askStatus);

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        expectedState.setCurrentTaskIndex(3);
        List <Task> expectList = new ArrayList<>(tasks);
        expectList.remove(2);
        String[] expectedTasks = expectList.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput("ARCH", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }

    @Test
    void processAskStatusInvalid () {
        State state = new State();
        state.setCorrectOwner("user");

        State expectedState = new State();
        expectedState.setCorrectOwner("user");
        String[] expectedTasks = tasks.stream().map(Task::toString).toArray(String[]::new);
        IntStream.range(0, expectedTasks.length).forEach(i -> expectedTasks[i] = i + 1 + " " + expectedTasks[i]);

        String[] actualTasks = StateService.processUserInput( "arc", state);

        Assertions.assertArrayEquals(expectedTasks, actualTasks);
        Assertions.assertEquals(expectedState, state);
    }
}