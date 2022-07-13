package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.businessLogicLayer.StateService;
import ru.pylaev.toDoProject.presentLayer.BaseUI;

@Controller
public class HtmlController extends BaseUI {

    @Autowired
    public HtmlController(State state) {
        super(state);
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("view", view);
        return "home";
    }

    @PostMapping
    public String post(@RequestParam String userInput) {
        view.setTasks(StateService.processUserInput(userInput, state));
        view.setMessage(state.getStep().toString());
        return "redirect:/";
    }
}
