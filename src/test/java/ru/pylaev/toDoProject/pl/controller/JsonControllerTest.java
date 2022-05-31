package ru.pylaev.toDoProject.pl.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dal.Task;
import ru.pylaev.toDoProject.pl.view.View;
import ru.pylaev.util.DBDataSupplier;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class JsonControllerTest {

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");

    @Autowired
    private MockMvc mvc;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        DBDataSupplier.prepareData("PrepareData.sql");
    }

    @Test
    void processUserInput () throws Exception {
        Task task1 = new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT");
        Task task2 = new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT");
        Task task3 = new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE");

        tasks.clear();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasksAsList(tasks);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : expectedView.getArrTasks()) {
            stringBuilder.append(s).append("\n");
        }

        String expectedResult = stringBuilder + expectedView.getMessage();

        this.mvc.perform(post("/sendJson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"user\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}