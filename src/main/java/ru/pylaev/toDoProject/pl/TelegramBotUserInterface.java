package ru.pylaev.toDoProject.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.bll.UserInputService;
import ru.pylaev.toDoProject.pl.controller.UiHandler;
import ru.pylaev.toDoProject.pl.view.View;

import java.util.Arrays;

@Component
public class TelegramBotUserInterface implements Runnable {
    private final View view;
    private final UserInputService userInputService;

    private final ru.pylaev.toDoProject.pl.TelegramBot bot;

    @Autowired
    public TelegramBotUserInterface(View view, UserInputService userInputService, @Value("${botToken}") String token){
        this.view = view;
        this.userInputService = userInputService;
        bot = new ru.pylaev.toDoProject.pl.TelegramBot(token, 1249988927);
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) { e.printStackTrace(); }
        bot.send(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"));
    }

    public void run () {
        while (true) {
            bot.Input = null;
            try {
                while (bot.Input == null) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UiHandler.processUserInput(bot.Input, view, userInputService);
            bot.send(Arrays.toString(view.getArrTasks()));
            bot.send(view.getMessage());
        }
    }
}
