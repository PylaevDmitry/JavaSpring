package ru.pylaev.toDoProject.dal.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pylaev.toDoProject.ToDoMain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

class DbTasksDaoTest {

    @Autowired
    DbTasksDao dbTasksDao;

    @BeforeAll
    static void setUp() throws IOException, URISyntaxException {
        URI prepareDataPath = Objects.requireNonNull(DbTasksDaoTest.class.getClassLoader()
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
    void DbTaskDao_Add_Adds_new_task() {

    }

    @Test
    void DbTaskDao_Add_Throw_if_null_passed() {
        // Arrenge

        // Act

        // Assert
    }

    @Test
    void getAll ( ) {
    }

    @Test
    void add ( ) {
    }

    @Test
    void setStatus ( ) {
    }
}