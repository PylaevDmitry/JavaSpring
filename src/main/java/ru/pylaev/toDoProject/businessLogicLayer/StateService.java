package ru.pylaev.toDoProject.businessLogicLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.util.InputChecker;
import ru.pylaev.util.ListToNumberingArrayConverter;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateService {
    private static TaskRepository taskRepository;
    private static List<Task> tasks = new ArrayList<>();

    @Autowired
    public void setTaskRepository (TaskRepository t) {
        taskRepository = t;
    }

    public static String[] processUserInput(String userInput, State state) {
        if (userInput==null) {
            return new String[]{};
        }
        else if (userInput.equals("EXIT")) {
            state.reset();
            return new String[]{};
        }

        if (state.getOwner()==null) {
            state.setCorrectOwner(userInput);
        }

        return  getStepResult(userInput, state);
    }

    private static String[] getStepResult(String userInput, State state) {
        switch (state.getStep()) {
            case askNumber -> {
                List<Task> tasksList = taskRepository.findByOwner(state.getOwner());
                int index = validateIndex(userInput, tasksList.size());
                if (index == 0) state.setStep(Step.askNew);
                else if (index > 0) {
                    state.setStep(Step.askStatus);
                    state.setCurrentTaskIndex(index);
                }
                tasks = tasksList;
            }
            case askNew -> {
                int saveNewResult = taskRepository.saveNewTask(state.getOwner(), userInput);
                state.setStep(Step.askNumber);
                if (saveNewResult>0) tasks = taskRepository.findByOwner(state.getOwner());
            }
            case askStatus -> {
                int changeStatusResult = taskRepository.updateTaskStatus(state.getOwner(), userInput, state.getCurrentTaskIndex());
                if (changeStatusResult>0) {
                    state.setStep(Step.askNumber);
                    tasks = taskRepository.findByOwner(state.getOwner());
                }
                else if (changeStatusResult==0) state.setStep(Step.askNumber);
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
