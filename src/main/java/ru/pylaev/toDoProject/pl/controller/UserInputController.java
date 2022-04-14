package ru.pylaev.toDoProject.pl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.bll.UserInputService;
import ru.pylaev.toDoProject.dal.entity.Task;
import ru.pylaev.toDoProject.pl.view.View;

import java.util.List;
import java.util.Objects;

@Controller
public class UserInputController {
    private static final String askOwner = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner");
    private static final String askNumber = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber");
    private static final String askNew = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew");
    private static final String askStatus = ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus");

    private final View view;
    private final UserInputService userInputService;

    @Autowired
    public UserInputController (View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
    }

    @GetMapping
    public String show (Model model) {
        model.addAttribute("view", view);
        return "home";
    }

    @PostMapping
    public String processUserInput (@RequestParam String userInput) {
        processView(userInput);
        return "redirect:/";
    }

    public void processView (String userInput) {
        if (view.getMessage().equals(askOwner) && userInputService.checkOwner(view.getOwner(), userInput)) {
            view.setOwner(userInput);
            view.setMessage(askNumber);
        }

        if (Objects.isNull(userInput)) {
            return;
        }

        if (view.getMessage().equals(askNumber)) {
            List<Task> tasks = userInputService.getActualTasks(view.getOwner());
            int getCurrentIndexResult = userInputService.getCurrentIndex(userInput, tasks.size());
            if (getCurrentIndexResult == 0) {
                view.setMessage(askNew);
            }
            else if (getCurrentIndexResult > 0) {
                view.setMessage(askStatus);
                view.setTaskIndex(getCurrentIndexResult);
            }
            view.setTasksAsList(tasks);
        }
        else if (view.getMessage().equals(askNew)) {
            int saveNewResult = userInputService.saveNew(view.getOwner(), userInput);
            view.setMessage(askNumber);
            if (saveNewResult>0){
                view.setTasksAsList(userInputService.getActualTasks(view.getOwner()));
            }
        }
        else if (view.getMessage().equals(askStatus)) {
            int changeStatusResult = userInputService.changeStatus(view.getOwner(), userInput, view.getTaskIndex());
            if (changeStatusResult>0) {
                view.setMessage(askNumber);
                view.setTasksAsList(userInputService.getActualTasks(view.getOwner()));
            }
            else if (changeStatusResult==0) view.setMessage(askNumber);
        }
    }
}
