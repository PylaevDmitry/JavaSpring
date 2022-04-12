package ru.pylaev.toDoProject.dal.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class DbTasksDaoTest {

    @Autowired
    private DbTasksDao dao;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        URI sqlPath = Objects.requireNonNull(DbTasksDaoTest.class.getClassLoader()
                        .getResource("Truncate.sql"))
                        .toURI();
        List<String> list = Files.readAllLines(Paths.get(sqlPath));
        String sql = String.join("", list);

        try (Connection dbConnection = ConnectionBuilder.getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }

        dao.setOwner("user");
    }

    @Test
    void Get_All_Add_new_task() {
        Task task1 = new Task("user","note1", new Date(),"WAIT");

        dao.add(task1);

        Task task2 = dao.getAll()[0];
        Assertions.assertEquals(dao.getAll().length, 1);
        Assertions.assertEquals(task1, task2);
    }

    @Test
    void Add_Throw_if_null_passed() {
        var expectedException = Assertions.assertThrows(NullPointerException.class, () -> dao.add(null));

        Assertions.assertTrue(expectedException.getMessage().contains("null"));
    }

    @Test
    void setStatus ( ) {
        Task task1 = new Task("user","note1", new Date(),"WAIT");
        dao.add(task1);
        Task task2 = dao.getAll()[0];
        task1 = new Task(String.valueOf(task2.getId()), "user","note1", task1.getDate(), "DONE");

        dao.setStatus(task2.getId(), "DONE");

        task2 = dao.getAll()[0];
        Assertions.assertEquals(task1, task2);
    }
}