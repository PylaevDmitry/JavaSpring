package ru.pylaev.toDoProject.bll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.dao.ConnectionBuilder;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserInputServiceTest {

    @Autowired
    private TaskRepository taskRepository;

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
    }

    @Test
    void processNew ( ) {
        UserInputService userInputService = new UserInputService(taskRepository);
        userInputService.checkOwner("user");
        userInputService.process("user");
        userInputService.process("note1");
        userInputService.process("NEW");
        View resultView = userInputService.process("note2");
        String[] real = resultView.getArrTasks();

        String[] expectArr = new String[2];
        expectArr[0] = "1 note1 " + String.valueOf(new Date()).substring(0, 16) + " WAIT";
        expectArr[1] = "2 note2 " + String.valueOf(new Date()).substring(0, 16) + " WAIT";

        Assertions.assertTrue(Arrays.deepEquals(real, expectArr));
    }

    @Test
    void processArch ( ) {
        UserInputService userInputService = new UserInputService(taskRepository);
        userInputService.checkOwner("user");
        userInputService.process("user");
        userInputService.process("note1");
        userInputService.process("NEW");
        userInputService.process("note2");
        userInputService.process("1");
        View resultView = userInputService.process("ARCH");
        String[] real = resultView.getArrTasks();

        String[] expectArr = new String[1];
        expectArr[0] = "1 note2 " + String.valueOf(new Date()).substring(0, 16) + " WAIT";

        Assertions.assertTrue(Arrays.deepEquals(real, expectArr));
    }

    @Test
    void processDone ( ) {
        UserInputService userInputService = new UserInputService(taskRepository);
        userInputService.checkOwner("user");
        userInputService.process("user");
        userInputService.process("note1");
        userInputService.process("1");
        View resultView = userInputService.process("DONE");
        String[] real = resultView.getArrTasks();

        String[] expectArr = new String[1];
        expectArr[0] = "1 note1 " + String.valueOf(new Date()).substring(0, 16) + " DONE";

        Assertions.assertTrue(Arrays.deepEquals(real, expectArr));
    }

    @Test
    void processBack ( ) {
        UserInputService userInputService = new UserInputService(taskRepository);
        userInputService.checkOwner("user");
        userInputService.process("user");
        userInputService.process("note1");
        userInputService.process("1");
        View resultView = userInputService.process("BACK");

        String expectString = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
        String realString = resultView.getMessage();

        Assertions.assertEquals(expectString, realString);
    }
}