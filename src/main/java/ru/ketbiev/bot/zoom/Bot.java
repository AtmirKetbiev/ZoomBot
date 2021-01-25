package ru.ketbiev.bot.zoom;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.ketbiev.bot.zoom.botController.MainController;

public class Bot extends AbilityBot {
    Bot(DefaultBotOptions botOptions) {
        super(Const.TOKEN_BOT, Const.BOT_USERNAME, botOptions);
    }

    public AbilityExtension abilityS() {
        return new MainController(silent);
    }

    @Override
    public int creatorId() {
        return 0;
    }
    @Override
    public String getBotUsername() {
        return Const.BOT_USERNAME;
    }
    @Override
    public String getBotToken() {
        return Const.TOKEN_BOT;
    }
}
