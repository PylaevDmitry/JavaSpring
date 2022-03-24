package ru.pylaev.toDoProject.bll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.dao.ConnectionBuilder;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.dal.repo.TaskRepository;
import ru.pylaev.toDoProject.pl.view.View;
import ru.pylaev.util.Checker;

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

    private static final String[] commands = new String[] {"ARCH", "DONE", "WAIT", "BACK", "EXIT"};
    private static final String[] tasksStates = new String[] {"ARCH", "DONE", "WAIT"};
    private static final String[] invalidNameSymbols = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    @Mock
    private TaskRepository taskRepository;

    List<Task> tasks = new ArrayList<>();

//    @BeforeEach
//    void setUp() throws IOException, URISyntaxException {
//        URI prepareDataPath = Objects.requireNonNull(UserInputServiceTest.class.getClassLoader()
//                        .getResource("PrepareData.sql"))
//                .toURI();
//        List<String> list = Files.readAllLines(Paths.get(prepareDataPath));
//        String sql = String.join("", list);
//
//        try (Connection dbConnection = ConnectionBuilder.getDbConnection()) {
//            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
//        }
//    }

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
        expectedView.setMessage(askNumber);
        View view = new View();
        UserInputService userInputService = new UserInputService(taskRepository);

        view = userInputService.howToServe(view, "user");

        Assertions.assertEquals(view, expectedView);
    }

    private void prepareData () {
        tasks.add(new Task("11", "user", "note1", "wed", "WAIT"));
        tasks.add(new Task("14", "user", "note2", "thu", "DONE"));
        tasks.add(new Task("3", "user", "note3", "wed", "WAIT"));
        Mockito.when(taskRepository.findByOwner("user")).thenReturn(tasks);
        Mockito.when(taskRepository.findById(11L)).thenReturn(Optional.of(new Task("11", "user", "note1", "wed", "WAIT")));
        Mockito.when(taskRepository.findById(14L)).thenReturn(Optional.of(new Task("14", "user", "note2", "thu", "DONE")));
        Mockito.when(taskRepository.findById(3L)).thenReturn(Optional.of(new Task("3", "user", "note3", "wed", "WAIT")));
    }

    @Test
    void processAskNew () {
        prepareData();
        View expectedView = new View();
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        View view = new View();
        UserInputService userInputService = new UserInputService(taskRepository);

        view = userInputService.howToServe(view, "user");
        view = userInputService.howToServe(view, "note1");

        Assertions.assertEquals(view, expectedView);

    }

//
//    @Test
//    void processNew ( ) {
//        View view = new View();
//        UserInputService userInputService = new UserInputService(taskRepository);
//        userInputService.checkOwner(view,"user");
//        userInputService.process(view,"user");
//        userInputService.process(view,"note1");
//        userInputService.process(view,"NEW");
//        View resultView = userInputService.process(view,"note2");
//        String[] real = resultView.getArrTasks();
//
//        String[] expectArr = new String[2];
//        expectArr[0] = "1 note1 " + String.valueOf(new Date()).substring(0, 16) + " WAIT";
//        expectArr[1] = "2 note2 " + String.valueOf(new Date()).substring(0, 16) + " WAIT";
//
//        Assertions.assertTrue(Arrays.deepEquals(real, expectArr));
//    }
//
//    @Test
//    void processArch ( ) {
//        View view = new View();
//        UserInputService userInputService = new UserInputService(taskRepository);
//        userInputService.checkOwner(view,"user");
//        userInputService.process(view,"user");
//        userInputService.process(view,"note1");
//        userInputService.process(view,"NEW");
//        userInputService.process(view,"note2");
//        userInputService.process(view,"1");
//        View resultView = userInputService.process(view,"ARCH");
//        String[] real = resultView.getArrTasks();
//
//        String[] expectArr = new String[1];
//        expectArr[0] = "1 note2 " + String.valueOf(new Date()).substring(0, 16) + " WAIT";
//
//        Assertions.assertTrue(Arrays.deepEquals(real, expectArr));
//    }
//
//    @Test
//    void processDone ( ) {
//        View view = new View();
//        UserInputService userInputService = new UserInputService(taskRepository);
//        userInputService.checkOwner(view,"user");
//        userInputService.process(view,"user");
//        userInputService.process(view,"note1");
//        userInputService.process(view,"1");
//        View resultView = userInputService.process(view,"DONE");
//        String[] real = resultView.getArrTasks();
//
//        String[] expectArr = new String[1];
//        expectArr[0] = "1 note1 " + String.valueOf(new Date()).substring(0, 16) + " DONE";
//
//        Assertions.assertTrue(Arrays.deepEquals(real, expectArr));
//    }
//
//    @Test
//    void processBack ( ) {
//        View view = new View();
//        UserInputService userInputService = new UserInputService(taskRepository);
//        userInputService.checkOwner(view,"user");
//        userInputService.process(view,"user");
//        userInputService.process(view,"note1");
//        userInputService.process(view,"1");
//        View resultView = userInputService.process(view,"BACK");
//
//        String expectString = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
//        String realString = resultView.getMessage();
//
//        Assertions.assertEquals(expectString, realString);
//    }
}