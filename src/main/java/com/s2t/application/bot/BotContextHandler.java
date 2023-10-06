package com.s2t.application.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.s2t.application.util.StringConstants.*;

@RequiredArgsConstructor
public class BotContextHandler {
    private final TelegramBot bot;
    private final List<BotCommand> commandList = Arrays.stream(com.s2t.application.bot.BotCommand.values())
            .map(cmd -> new BotCommand(cmd.getCommand(), cmd.getMessage()))
            .collect(Collectors.toList());

//    public BotContextHandler(TelegramBot bot) throws TelegramApiException {
//        this.bot = bot;
//    }

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
