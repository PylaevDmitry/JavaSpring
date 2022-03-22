package ru.pylaev.toDoProject.dal.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pylaev.util.CustomProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@Component
public class ConnectionBuilder {

    private static final CustomProperties APPLICATION_PROPERTIES = new CustomProperties("application");
    private static final String dbUser = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.username");
    private static final String dbPass = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.password");

//    private static String dbUser;
//    private static String dbPass;
//
//    @Value("${spring.datasource.username}")
//    public void setDbUser (String input) {
//        dbUser = input;
//    }
//
//    @Value("${spring.datasource.password}")
//    public void setDbPass (String input) {
//        dbPass = input;
//    }

    public static Connection getDbConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo", dbUser, dbPass);
    }
}
