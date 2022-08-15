package com.s2t.application.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.extern.slf4j.Slf4j;

import static com.s2t.application.util.StringConstants.*;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.username}")
    private String telegramBotUsername;

    @Override
    public String getBotUsername() {
        return telegramBotUsername;
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            executeNextAction(chatId, messageText);
        }
    }

    public void sendMessage(long chatId, String messageText) {
        SendMessage message = SendMessage.builder().chatId(chatId).text(messageText).build();
        sendMessage(message);
    }

    private void executeNextAction(long chatId, String messageText) {
        if(messageText.startsWith("/")) {
            processCommand(chatId, messageText.substring(1));
        } else {
            processMessage(chatId, messageText);
        }
    }

    private void processMessage(long chatId, String messageText) {
        sendMessage(chatId, messageText);
    }

    private void processCommand(long chatId, String substring) {
        switch (substring) {
            case START:
                sendMessage(chatId, WELCOME_MESSAGE);
                break;
            case READ:
                sendMessage(chatId, READING_SMS_MESSAGE);
                break;
            case STOP_READ:
                sendMessage(chatId, STOP_READING_SMS_MESSAGE);
                break;
            default:
                sendMessage(chatId, COMMAND_UNKNOWN_MESSAGE);
                break;
        }
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
