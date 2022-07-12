package ru.pylaev.toDoProject.presentLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.List;
import java.util.stream.IntStream;

@Controller
public class HtmlController {
    private final View view;
    private final TaskRepository taskRepository;
    private String[] arrTasks;

    @Autowired
    public HtmlController(View view, TaskRepository taskRepository) {
        this.view = view;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String showStartView (Model model) {
        model.addAttribute("view", view);
        return "home";
    }

    @PostMapping
    public String processUserInput (@RequestParam String userInput) {
        List<Task> list = ViewHandler.processUserInput(userInput, view, taskRepository);
        arrTasks = new String[list.size()];
        IntStream.range(0, list.size()).forEach(i -> arrTasks[i] = i + 1 + " " + list.get(i));

        return "redirect:/";
    }
}
