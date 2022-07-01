package ru.pylaev.toDoProject.businessLogicLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.dataAccessLayer.fileIO.FileTasksDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserInputServiceTest {

    @MockBean
    private FileTasksDAO tasksDAO;

    @Autowired
    UserInputService userInputService;

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
        Mockito.when(tasksDAO.findByOwner("user")).thenReturn(tasks);

    }

    @Test
    void getActualTasks() {
        assertEquals(userInputService.getActualTasks("user"), tasks);
    }

    @Test
    void checkOwnerIsOk() {
        assertTrue(userInputService.checkOwner(null, "user"));
    }

    @Test
    void checkOwnerIsRejected() {
        assertFalse(userInputService.checkOwner(null, ":"));
    }

    @Test
    void getCurrentIndexIsOk() {
        assertEquals(userInputService.getCurrentIndex("3", tasks.size()), 3);
    }

    @Test
    void getCurrentIndexIsNull() {
        assertEquals(userInputService.getCurrentIndex("2", 0), 0);
    }

    @Test
    void getCurrentIndexIsRejected() {
        assertEquals(userInputService.getCurrentIndex("4", tasks.size()), -1);
    }

    @Test
    void saveNewIsOk() {
        assertEquals(userInputService.saveNew("user", "note1"), 1);
    }

    @Test
    void saveNewIsRejected() {
        assertEquals(userInputService.saveNew("user", "BACK"), 0);
    }

    @Test
    void changeStatusOK() {
        assertEquals(userInputService.changeStatus("user", "DONE", 1), 1);
    }

    @Test
    void changeStatusBack() {
        assertEquals(userInputService.changeStatus("user", "BACK", 1), 0);
    }

    @Test
    void changeStatusReject() {
        assertEquals(userInputService.changeStatus("user", "invalidStatus", 1), -1);
    }
}