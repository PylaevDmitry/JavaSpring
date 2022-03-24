package ru.pylaev.toDoProject.pl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.bll.UserInputService;
import ru.pylaev.toDoProject.pl.view.UserInput;
import ru.pylaev.toDoProject.pl.view.View;

@org.springframework.web.bind.annotation.RestController
public class UserInputRestController {

    private static final String askOwner = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    private final View view;
    private final UserInputService userInputService;

    @Autowired
    public UserInputRestController (View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
    }

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody UserInput userInput) {
        try {
            userInputService.howToServe(view, userInput.getContent());

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
