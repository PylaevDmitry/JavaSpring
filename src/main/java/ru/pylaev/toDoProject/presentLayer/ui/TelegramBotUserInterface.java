package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.UiHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;

//@Component
public class TelegramBotUserInterface implements Runnable {
    private final View view;
    private final UserInputService userInputService;

    private final TelegramBot bot;

    @Autowired
    public TelegramBotUserInterface(View view, UserInputService userInputService, @Value("${botToken}") String token){
        this.view = view;
        this.userInputService = userInputService;
        bot = new TelegramBot(token, 1249988927);
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) { e.printStackTrace(); }
        bot.send(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"));
    }

    @Override
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
