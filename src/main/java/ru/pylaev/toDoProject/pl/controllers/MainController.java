package ru.pylaev.toDoProject.pl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.bll.service.UserInputService;
import ru.pylaev.toDoProject.pl.view.View;

@Controller
public class MainController {

    @Autowired
    private View view;

    @Autowired
    private UserInputService userInputService;

    @GetMapping
    public String show (Model model) {
        model.addAttribute("view", view);
        return "home";
    }

    @PostMapping
    public String processUserInput (@RequestParam String userInput) {
        view = userInputService.process(userInput);
        return "redirect:/";
    }

}
