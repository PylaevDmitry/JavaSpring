package ru.pylaev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@Component
public class ConnectionBuilder {
    private static final CustomProperties APPLICATION_PROPERTIES = new CustomProperties("application");
    private static final String dbUser = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.username");
    private static final String dbPass = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.password");
    private static final String driver = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.driver-class-name");
    private static final String url = APPLICATION_PROPERTIES.getPropertyContent("spring.datasource.url");
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
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, dbUser, dbPass);
    }
}
