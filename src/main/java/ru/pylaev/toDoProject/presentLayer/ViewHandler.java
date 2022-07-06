package ru.pylaev.toDoProject.presentLayer;

import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewHandler {
    public static void processUserInput(String userInput, View view, UserInputService userInputService) {
        if (Objects.isNull(userInput)) {
            return;
        }

        if (userInput.equals("EXIT")) {
            view.setMessage(Messages.askOwner);
            view.setOwner(null);
            view.setTasksAsList(new ArrayList<>());
            view.setTaskIndex(0);
            return;
        }

        if (view.getMessage().equals(Messages.askOwner) && userInputService.checkOwner(view.getOwner(), userInput)) {
            view.setOwner(userInput);
            view.setMessage(Messages.askNumber);
        }

        if (view.getMessage().equals(Messages.askNumber)) {
            List<Task> tasks = userInputService.getActualTasks(view.getOwner());
            int getCurrentIndexResult = userInputService.getCurrentIndex(userInput, tasks.size());
            if (getCurrentIndexResult == 0) {
                view.setMessage(Messages.askNew);
            }
            else if (getCurrentIndexResult > 0) {
                view.setMessage(Messages.askStatus);
                view.setTaskIndex(getCurrentIndexResult);
            }
            view.setTasksAsList(tasks);
        }
        else if (view.getMessage().equals(Messages.askNew)) {
            int saveNewResult = userInputService.saveNew(view.getOwner(), userInput);
            view.setMessage(Messages.askNumber);
            if (saveNewResult>0){
                view.setTasksAsList(userInputService.getActualTasks(view.getOwner()));
            }
        }
        else if (view.getMessage().equals(Messages.askStatus)) {
            int changeStatusResult = userInputService.changeStatus(view.getOwner(), userInput, view.getTaskIndex());
            if (changeStatusResult>0) {
                view.setMessage(Messages.askNumber);
                view.setTasksAsList(userInputService.getActualTasks(view.getOwner()));
            }
            else if (changeStatusResult==0) view.setMessage(Messages.askNumber);
        }
    }
}
