package ru.pylaev.toDoProject.dal.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.util.CustomProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

//@SpringBootTest
class FileTasksDaoTest {

    private static final CustomProperties APPLICATION_PROPERTIES = new CustomProperties("application");
    private static final String path = APPLICATION_PROPERTIES.getPropertyContent("filePath");

//    @Value("${filePath}")
//    private String path;

    @BeforeEach
    void setUp() throws IOException {
        if (Files.exists(Paths.get(path))) Files.delete(Paths.get(path));
    }

    @Test
    void Get_All_Add_new_task() {
        Dao dao = new FileTasksDao(path);
        dao.setOwner("user");
        Task task1 = new Task("user","note1", new Date(),"WAIT");

        dao.add(task1);
        Task task2 = dao.getAll()[0];

        Assertions.assertEquals(task1, task2);
    }

    @Test
    void Add_Throw_if_null_passed() {
        Dao dao = new FileTasksDao(path);
        dao.setOwner("user");
        Task task1 = null;

        var expectedException = Assertions.assertThrows(NullPointerException.class, () -> dao.add(task1));

        Assertions.assertTrue(expectedException.getMessage().contains("null"));
    }

    @Test
    void setStatus ( ) {
        Dao dao = new FileTasksDao(path);
        dao.setOwner("user");

        Task task1 = new Task("user","note1", new Date(),"WAIT");
        dao.add(task1);
        Task task2 = dao.getAll()[0];

        task1 = new Task(String.valueOf(task2.getId()), "user","note1", task1.getDate(), "DONE");
        dao.setStatus(task2.getId(), "DONE");
        task2 = dao.getAll()[0];

        Assertions.assertEquals(task1, task2);
    }
}