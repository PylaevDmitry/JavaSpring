package ru.pylaev.toDoProject.pl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.pl.view.UserInput;
import ru.pylaev.toDoProject.pl.view.View;

@Controller
public class JsonController {
    private final View view;
    private final ControllerHandler controllerHandler;

    @Autowired
    public JsonController(View view, ControllerHandler controllerHandler) {
        this.view = view;
        this.controllerHandler = controllerHandler;
    }

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody UserInput userInput) {
        try {
            controllerHandler.processView (userInput.getContent(), view);

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
