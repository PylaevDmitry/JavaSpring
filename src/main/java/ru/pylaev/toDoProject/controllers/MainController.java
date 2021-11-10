package ru.pylaev.toDoProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserInputService userInputService;

    @GetMapping
    public String show (Model model) {
        model.addAttribute("arrTasks", userInputService.getArrTasks());
        model.addAttribute("message", userInputService.getMessage());
        return "home";
    }

    @PostMapping
    public String processUserInput (@RequestParam String userInput) {
        userInputService.process(userInput);
        return "redirect:/";
    }

}
