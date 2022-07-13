package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.businessLogicLayer.StateService;
import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.presentLayer.view.JsonInput;
import ru.pylaev.toDoProject.presentLayer.view.View;

@Controller
public class JsonController {
    private final State state;
    private final View view = new View();

    @Autowired
    public JsonController(State state, TaskRepository taskRepository) {
        this.state = state;
    }

    @PostMapping("/sendJson")
    public ResponseEntity<String> processUserInput (@RequestBody JsonInput jsonInput) {
        try {
            view.setTasks(StateService.processUserInput(jsonInput.getContent(), state));
            view.setMessage(state.getStep().toString());

            StringBuilder stringBuilder = new StringBuilder();
            for (String s : view.getTasks()) {
                stringBuilder.append(s).append("\n");
            }
            return ResponseEntity.ok(stringBuilder + view.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("networkError") + " " + e.getMessage());
        }
    }
}
