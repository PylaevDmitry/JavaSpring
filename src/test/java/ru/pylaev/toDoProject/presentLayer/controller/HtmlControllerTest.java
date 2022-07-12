package ru.pylaev.toDoProject.presentLayer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.Messages;
import ru.pylaev.toDoProject.presentLayer.view.View;
import ru.pylaev.util.DBDataSupplier;
import ru.pylaev.util.HeadlessSpringBootContextLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = HeadlessSpringBootContextLoader.class)
class HtmlControllerTest {
    @Autowired
    private MockMvc mvc;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        DBDataSupplier.prepareData("PrepareData.sql");
    }

    @Test
    void show() throws Exception {
        View expectedView = new View();

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("view"))
                .andExpect(model().attribute("view", expectedView));
    }

    @Test
    void processUserInput() throws Exception {
        Task task1 = new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT");
        Task task2 = new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT");
        Task task3 = new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE");

        tasks.clear();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(Messages.askNumber);
        expectedView.setTasks(tasks);

        this.mvc.perform(post("/").param("userInput", "user"))
                .andExpect(status().is(302));

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("view"))
                .andExpect(model().attribute("view", expectedView));
    }
}