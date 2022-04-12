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

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class UserInputRestControllerTest {

    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");

    // 1. Act Arrange Assert
    // 2. Naming
    // 3. Unit, integration, API(component), Performance
    // 4. Mocking
    // 5. Coverage
    // 6. Data generators(ready libraries)
    // 7. Test must not dictate how to write prod code
    // 8. TDD
    @Autowired
    private MockMvc mvc;

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
    void processUserInput () throws Exception {
        View view = new View();

        View expectedView = new View();
        expectedView.setOwner("user");
        expectedView.setMessage(askNumber);
        expectedView.setTasks(tasks);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : expectedView.getArrTasks()) {
            stringBuilder.append(s).append("\n");
        }

        String expectedResult = stringBuilder + expectedView.getMessage();

        Mockito.when(userInputService.howToServe(view, "user")).thenReturn(expectedView);

        this.mvc.perform(post("/sendJson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"user\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}