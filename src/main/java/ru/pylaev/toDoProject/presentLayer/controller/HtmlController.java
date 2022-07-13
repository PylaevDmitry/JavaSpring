package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.businessLogicLayer.UiState;
import ru.pylaev.toDoProject.businessLogicLayer.UiStateService;
import ru.pylaev.toDoProject.presentLayer.view.View;

@Controller
public class HtmlController {
    private final UiState uiState;
    private final View view = new View();

    @Autowired
    public HtmlController(UiState uiState) {
        this.uiState = uiState;
    }

    @GetMapping
    public String showStartView (Model model) {
        model.addAttribute("message", uiState.getStep());
        model.addAttribute("tasks", view.getTasks());
        return "home";
    }

    @PostMapping
    public String processUserInput (@RequestParam String userInput) {
        view.setTasks(UiStateService.processUserInput(userInput, uiState));
        view.setMessage(uiState.getStep().toString());
        return "redirect:/";
    }
}
