package ru.pylaev.toDoProject.bll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.dao.ConnectionBuilder;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.dal.repo.TaskRepository;
import ru.pylaev.toDoProject.pl.view.View;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserInputServiceTest {

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    @Mock
    private TaskRepository taskRepository;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        URI prepareDataPath = Objects.requireNonNull(UserInputServiceTest.class.getClassLoader()
                        .getResource("PrepareData.sql"))
                .toURI();
        List<String> list = Files.readAllLines(Paths.get(prepareDataPath));
        String sql = String.join("", list);

        try (Connection dbConnection = ConnectionBuilder.getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }

        tasks.add(new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT"));
        tasks.add(new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE"));
        tasks.add(new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT"));
        Mockito.when(taskRepository.findByOwner("user")).thenReturn(tasks);
        Mockito.when(taskRepository.findById(11L)).thenReturn(Optional.of(new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT")));
        Mockito.when(taskRepository.findById(14L)).thenReturn(Optional.of(new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE")));
        Mockito.when(taskRepository.findById(3L)).thenReturn(Optional.of(new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT")));
    }

    @Test
    void checkOwnerInvalidSymbol () {
        View expectedView = new View();

        View view = new View();

        UserInputService userInputService = new UserInputService(taskRepository);

        view = userInputService.howToServe(view, "???");

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void checkOwnerNull () {
        View expectedView = new View();

        View view = new View();

        UserInputService userInputService = new UserInputService(taskRepository);

        view = userInputService.howToServe(view, null);

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void checkOwnerIsOk () {
        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        View view = new View();

        UserInputService userInputService = new UserInputService(taskRepository);

        view = userInputService.howToServe(view, "user");

        Assertions.assertEquals(view, expectedView);
    }

    @Test
    void processAskNumberOk () {
        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(1);
        expectedView.setMessage(askStatus);
        expectedView.setTasks(tasks);

        View view = new View();
        view.setOwner("user");
        view.setMessage(askNumber);

        UserInputService userInputService = new UserInputService(taskRepository);

        view = userInputService.howToServe(view, "1");

        Assertions.assertEquals(view, expectedView);
    }
}