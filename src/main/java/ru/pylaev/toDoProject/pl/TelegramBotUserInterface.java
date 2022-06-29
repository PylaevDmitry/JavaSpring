package ru.pylaev.toDoProject.pl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBotUserInterface implements Presenter {

    private final ru.pylaev.toDoProject.pl.presenter.TelegramBot bot;

    public TelegramBotUserInterface (@Value("${botToken}") String token){
        bot = new ru.pylaev.toDoProject.pl.presenter.TelegramBot(token, 1249988927);
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) { e.printStackTrace(); }
    }

    @Override
    public String askInput(String message) {
        bot.Input = null;
        try {
            bot.send(message);
            while (bot.Input == null) { Thread.sleep(100); }
        } catch (InterruptedException e) { e.printStackTrace();}
        return bot.Input;
    }

    @Override
    public void show(String message) { bot.send(message); }

    @Override
    public boolean isRunning ( ) {
        return true;
    }
}
