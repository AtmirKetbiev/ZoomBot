package ru.ketbiev.bot.zoom;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class App {

    final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            logger.info("Start bot");
            telegramBotsApi.registerBot(new Bot(botOptions));
        } catch (TelegramApiException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
