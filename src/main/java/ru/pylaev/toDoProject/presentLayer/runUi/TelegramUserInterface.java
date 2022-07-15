package ru.pylaev.toDoProject.presentLayer.runUi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.businessLogicLayer.State;
import ru.pylaev.toDoProject.businessLogicLayer.StateService;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
public class TelegramUserInterface extends RunUI {
    private final TelegramBot bot;

    @Autowired
    public TelegramUserInterface(State state, View view, @Value("${botToken}") String token){
        super(state, view);
        bot = new TelegramBot(token, 1249988927);
    }

    @Override
    public void showStartView() {
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        bot.send(view.getMessage());
    }

    @Override
    public void processUserInput() {
        bot.Input = null;
        try {
            while (bot.Input == null) TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        view.setTasks(StateService.processUserInput(bot.Input, state));
        view.setMessage(state.getStep().toString());
        if (view.getTasks().length>0) {
            bot.send(Arrays.toString(view.getTasks()));
        }
        bot.send(view.getMessage());
    }
}
