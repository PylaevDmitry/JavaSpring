package ru.pylaev.toDoProject.presentLayer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.businessLogicLayer.UserInputService;
import ru.pylaev.toDoProject.presentLayer.ViewHandler;
import ru.pylaev.toDoProject.presentLayer.view.View;

import java.util.Arrays;
import java.util.Objects;

//@Component
public class TelegramBotUserInterface extends UserInterface {
    private final TelegramBot bot;

    @Autowired
    public TelegramBotUserInterface(View view, UserInputService userInputService, @Value("${botToken}") String token){
        super(view, userInputService);
        bot = new TelegramBot(token, 1249988927);
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run () {
        bot.send(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner"));
        while (true) {
            bot.Input = null;
            try {
                while (bot.Input == null) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ViewHandler.processUserInput(bot.Input, view, userInputService);
            if (!Objects.isNull(view.getArrTasks())) {
                bot.send(Arrays.toString(view.getArrTasks()));
            }
            bot.send(view.getMessage());
        }
    }
}
