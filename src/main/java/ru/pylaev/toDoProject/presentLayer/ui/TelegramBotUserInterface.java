package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.businessLogicLayer.UiState;
import ru.pylaev.toDoProject.businessLogicLayer.UiStateService;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
public class TelegramBotUserInterface extends UserInterfaceBase {
    private final TelegramBot bot;

    @Autowired
    public TelegramBotUserInterface(UiState uiState, @Value("${botToken}") String token){
        super(uiState);
        bot = new TelegramBot(token, 1249988927);
    }

    @Override
    public void showStartView() {
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        bot.send(uiState.getStep().toString());
    }

    @Override
    public void processUserInput() {
        bot.Input = null;
        try {
            while (bot.Input == null) TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        view.setTasks(UiStateService.processUserInput(bot.Input, uiState));
        view.setMessage(uiState.getStep().toString());
        if (view.getTasks().length>0) {
            bot.send(Arrays.toString(view.getTasks()));
        }
        bot.send(view.getMessage());
    }
}
