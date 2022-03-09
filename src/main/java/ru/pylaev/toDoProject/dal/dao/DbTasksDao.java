package ru.pylaev.toDoProject.dal.dao;

import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.entity.Task;

import java.sql.*;
import java.util.ArrayList;

@Component
public class DbTasksDao implements Dao {
    private String owner;

    @Override
    public void setOwner (String owner) {
        this.owner = owner;
    }

    private Connection getDbConnection() throws SQLException {
        return ConnectionBuilder.getDbConnection();
    }

    @Override
    public Task[] getAll ( ) {
        String firstRequest = """
                CREATE TABLE IF NOT EXISTS task (
                id SERIAL,
                owner VARCHAR(50),
                text VARCHAR(50),
                date VARCHAR(50),
                status VARCHAR(50),
                PRIMARY KEY (id)
                );""";

        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(firstRequest);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }

        String request = "SELECT * FROM task WHERE owner=?;";
        var result = new ArrayList<Task>();
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, owner);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString("status").equals("ARCH")) {
                    result.add(new Task(resultSet.getString("id"),
                            resultSet.getString("owner"),
                            resultSet.getString("text"),
                            resultSet.getString("date"),
                            resultSet.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }

        if (result.size()==0) return new Task[0];
        else {
            Task[] resArr = new Task[result.size()];
            for (int i = 0; i < result.size(); i++) { resArr[i]= result.get(i); }
            return resArr;
        }
    }

    @Override
    public void add (Task data) {
        String request = "INSERT INTO task (owner, text, date, status) VALUES (?,?,?,?);";

        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request, new String[] {});
            preparedStatement.setString(1, data.getOwner());
            preparedStatement.setString(2, data.getText());
            preparedStatement.setString(3, data.getDate());
            preparedStatement.setString(4, data.getStatus());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) data.setId(generatedKeys.getLong("id"));

        } catch (SQLException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }

    @Override
    public void setStatus (long id, String status) {
        String request = "UPDATE task SET status=? WHERE id=?;";
        try (Connection dbConnection = getDbConnection()) {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("storageError"));
        }
    }
}


