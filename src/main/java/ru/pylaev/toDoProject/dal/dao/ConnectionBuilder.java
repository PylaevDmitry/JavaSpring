package ru.pylaev.toDoProject.dal.dao;

import ru.pylaev.util.CustomProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {

    private static final CustomProperties APPLICATION_PROPERTIES = new CustomProperties("application");
    private static final String dbUser = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.username");
    private static final String dbPass = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.password");

    public static Connection getDbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo", dbUser, dbPass);
    }
}
