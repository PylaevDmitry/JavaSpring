package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.businessLogicLayer.UiStateService;
import ru.pylaev.toDoProject.businessLogicLayer.UiState;
import ru.pylaev.toDoProject.presentLayer.view.UserInput;
import ru.pylaev.toDoProject.presentLayer.view.View;

@Controller
public class JsonController {
    private final UiState uiState;
    private final View view = new View();

    @Autowired
    public JsonController(UiState uiState, TaskRepository taskRepository) {
        this.uiState = uiState;
    }

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody UserInput userInput) {
        try {
            view.setTasks(UiStateService.processUserInput(userInput.getContent(), uiState));
            view.setMessage(uiState.getStep().toString());

            StringBuilder stringBuilder = new StringBuilder();
            for (String s : view.getTasks()) {
                stringBuilder.append(s).append("\n");
            }
            return ResponseEntity.ok(stringBuilder + view.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }
}
