package ru.pylaev.toDoProject.presentLayer.ui;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    public String Input;
    private final long ChatId;
    private final String Token;

    public TelegramBot(String token, long chatId){
        Token = token;
        ChatId = chatId;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getChat().getId() == ChatId)  {Input = update.getMessage().getText();}
    }

    @Override
    public String getBotUsername() { return "deadmoviestarTODO_bot"; }

    @Override
    public String getBotToken() { return Token; }

    public void send(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(ChatId));
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) { e.printStackTrace(); }
    }
}