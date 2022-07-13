package ru.pylaev.toDoProject.businessLogicLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.util.InputChecker;
import ru.pylaev.util.ListToNumberingArrayConverter;

import java.util.ArrayList;
import java.util.List;

@Service
public class UiStateService {
    private static TaskRepository taskRepository;
    private static List<Task> tasks = new ArrayList<>();

    @Autowired
    public void setTaskRepository (TaskRepository t) {
        taskRepository = t;
    }

    public static String[] processUserInput(String userInput, UiState uiState) {
        if (userInput==null) {
            return new String[]{};
        }
        else if (userInput.equals("EXIT")) {
            uiState.reset();
            return new String[]{};
        }

        if (uiState.getOwner()==null) {
            uiState.setCorrectOwner(userInput);
        }

        return  getStepResult(userInput, uiState);
    }

    private static String[] getStepResult(String userInput, UiState uiState) {
        switch (uiState.getStep()) {
            case askNumber -> {
                List<Task> tasksList = taskRepository.findByOwner(uiState.getOwner());
                int index = validateIndex(userInput, tasksList.size());
                if (index == 0) uiState.setStep(Step.askNew);
                else if (index > 0) {
                    uiState.setStep(Step.askStatus);
                    uiState.setCurrentTaskIndex(index);
                }
                tasks = tasksList;
            }
            case askNew -> {
                int saveNewResult = taskRepository.saveNewTask(uiState.getOwner(), userInput);
                uiState.setStep(Step.askNumber);
                if (saveNewResult>0) tasks = taskRepository.findByOwner(uiState.getOwner());
            }
            case askStatus -> {
                int changeStatusResult = taskRepository.updateTaskStatus(uiState.getOwner(), userInput, uiState.getCurrentTaskIndex());
                if (changeStatusResult>0) {
                    uiState.setStep(Step.askNumber);
                    tasks = taskRepository.findByOwner(uiState.getOwner());
                }
                else if (changeStatusResult==0) uiState.setStep(Step.askNumber);
            }
        }
        return ListToNumberingArrayConverter.convert(tasks);
    }

    private static int validateIndex(String userInput, int size) {
        if (size==0 || userInput.equals("NEW")) {
            return 0;
        }
        else if (!userInput.equals("BACK")) {
            var taskIndex = InputChecker.isValidIndex(userInput, size);
            if (taskIndex>-1) {
                return taskIndex;
            }
        }
        return -1;
    }
}
