package com.s2t.application.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

import static com.s2t.application.util.StringConstants.*;

public class BotContextHandler {
    private final TelegramBot bot;
    private final List<BotCommand> commandList = List.of(new BotCommand(START, "Welcome my children!"), new BotCommand(READ, "Start reading sms"), new BotCommand(STOP_READ, "Stop reading sms"));

    public BotContextHandler(TelegramBot bot) throws TelegramApiException {
        this.bot = bot;
    }

    public void handleContext() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        List<BotCommand> commandArrayList = this.bot.execute(new GetMyCommands());
        if (!commandArrayList.isEmpty()) {
            this.bot.execute(new DeleteMyCommands());
        }
        this.bot.execute(new SetMyCommands(commandList, null, null));
        telegramBotsApi.registerBot(this.bot);
    }

}
