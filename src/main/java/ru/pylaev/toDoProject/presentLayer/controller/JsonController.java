package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.UserInput;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.List;

@Controller
public class JsonController {
    private final View view;
    private final TaskRepository taskRepository;

    @Autowired
    public JsonController(View view, TaskRepository taskRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody UserInput userInput) {
        try {
            List<Task> tasks = ViewHandler.processUserInput(userInput.getContent(), view, taskRepository);

            StringBuilder stringBuilder = new StringBuilder();
            for (String s : tasks.stream().map(Task::toString).toList()) {
                stringBuilder.append(s).append("\n");
            }
            return ResponseEntity.ok(stringBuilder + view.getMessage().toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }
}
