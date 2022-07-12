package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.businessLogicLayer.TaskRepository;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TelegramBotUserInterface extends UserInterfaceBase {
    private final TelegramBot bot;

    @Autowired
    public TelegramBotUserInterface(View view, TaskRepository taskRepository, @Value("${botToken}") String token){
        super(view, taskRepository);
        bot = new TelegramBot(token, 1249988927);
    }

    @Override
    public void showStartView() {
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        bot.send(view.getMessage().toString());
    }

    @Override
    public void processUserInput() {
        bot.Input = null;
        try {
            while (bot.Input == null) TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Task> tasks = ViewHandler.processUserInput(bot.Input, view, taskRepository);
        if (tasks.size()>0) {
            bot.send(String.valueOf(tasks));
        }

        bot.send(view.getMessage().toString());
    }
}
