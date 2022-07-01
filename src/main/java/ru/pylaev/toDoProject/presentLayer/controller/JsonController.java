package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.UserInput;
import ru.pylaev.toDoProject.presentLayer.view.View;

@Controller
public class JsonController {
    private final View view;
    private final UserInputService userInputService;

    @Autowired
    public JsonController(View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
    }

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody UserInput userInput) {
        try {
            ViewHandler.processUserInput(userInput.getContent(), view, userInputService);

            StringBuilder stringBuilder = new StringBuilder();
            for (String s : view.getArrTasks()) {
                stringBuilder.append(s).append("\n");
            }
            return ResponseEntity.ok(stringBuilder + view.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }
}
