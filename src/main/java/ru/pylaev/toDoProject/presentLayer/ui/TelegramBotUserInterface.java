package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class TelegramBotUserInterface extends UserInterfaceBase {
    private final TelegramBot bot;

    @Autowired
    public TelegramBotUserInterface(View view, UserInputService userInputService, @Value("${botToken}") String token){
        super(view, userInputService);
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
        ViewHandler.processUserInput(bot.Input, view, userInputService);
        if (!Objects.isNull(view.getArrTasks())) {
            if (view.getArrTasks().length>0) {
                bot.send(Arrays.toString(view.getArrTasks()));
            }
        }
        bot.send(view.getMessage().toString());
    }
}
