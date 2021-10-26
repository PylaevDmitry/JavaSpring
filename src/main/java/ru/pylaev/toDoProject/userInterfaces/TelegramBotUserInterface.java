package ru.pylaev.toDoProject.userInterfaces;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.abstractions.IUserInterface;

public class TelegramBotUserInterface implements IUserInterface {

    private final TelegramBot bot;

    public TelegramBotUserInterface (String token){
        bot = new TelegramBot(token, 1249988927);
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
