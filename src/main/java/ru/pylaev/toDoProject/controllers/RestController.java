package ru.pylaev.toDoProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.models.UserInput;
import ru.pylaev.toDoProject.models.View;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private View view;

    @Autowired
    private UserInputService userInputService;

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody UserInput userInput) {
        try {
            view = userInputService.process(userInput.getContent());
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : view.getArrTasks()) {
                stringBuilder.append(s).append("\n");
            }
            return ResponseEntity.ok(stringBuilder + view.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

}
