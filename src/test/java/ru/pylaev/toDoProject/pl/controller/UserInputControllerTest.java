package ru.pylaev.toDoProject.pl.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.bll.UserInputService;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.pl.view.View;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class UserInputControllerTest {

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserInputService userInputService;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Task task1 = new Task("11", "user", "note1", "Wed Mar 24 16:01", "WAIT");
        Task task2 = new Task("14", "user", "note2", "Thu Mar 23 16:01", "DONE");
        Task task3 = new Task("3", "user", "note3", "Wed Mar 25 16:01", "WAIT");

        tasks.clear();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
    }

    @Test
    void processOwnerIsOk() throws Exception {
        View expectedView = new View();

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("view"))
                .andExpect(model().attribute("view", expectedView));
    }

    @Test
    void processAskNumberOk () throws Exception {
        View view = new View();

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        Mockito.when(userInputService.howToServe(view, "user")).thenReturn(expectedView);

        this.mvc.perform(post("/").param("userInput", "user"))
                .andExpect(status().is(302))
        ;

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("view"))
                .andExpect(model().attribute("view", expectedView));
    }

    @Test
    void processAskStatusOk () throws Exception {
        View view = new View();

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setTaskIndex(1);
        expectedView.setMessage(askStatus);
        expectedView.setTasks(tasks);

        Mockito.when(userInputService.howToServe(view, "1")).thenReturn(expectedView);

        this.mvc.perform(post("/").param("userInput", "1"))
                .andExpect(status().is(302))
        ;

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("view"))
                .andExpect(model().attribute("view", expectedView))
        ;
    }

}