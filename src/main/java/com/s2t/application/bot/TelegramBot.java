package com.s2t.application.bot;

import com.s2t.application.core.UserService;
import com.s2t.application.model.Cache;
import com.s2t.application.model.MapCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import lombok.extern.slf4j.Slf4j;

import static com.s2t.application.util.StringConstants.Message.COMMAND_UNKNOWN_MESSAGE;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.username}")
    private String telegramBotUsername;

    private final Cache<Long, String> otpCache;

    private final UserService userService;

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

    public void sendMessage(long userId, String messageText) {
        SendMessage message = SendMessage.builder().chatId(userId).text(messageText).build();
        sendMessage(message);
    }

    private void executeNextAction(long userId, String messageText) {
        if(messageText.startsWith("/")) {
            processCommand(userId, messageText.substring(1));
        } else {
            processMessage(userId, messageText);
        }
    }

    private void processCommand(long userId, String command) {
        String[] argList = command.split(" ");
        switch (BotCommand.getByCommand(argList[0])) {
            case START:
                processStartCommand(userId);
                break;
            case READ:
                processReadCommand(userId);
                break;
            case STOP_READ:
                processStopReadCommand(userId);
                break;
            case OTP:
                processOtpAndRegisterUser(userId, argList[1]);
                break;
            default:
                sendMessage(userId, COMMAND_UNKNOWN_MESSAGE);
                break;
        }
    }

    private void processStartCommand(long userId) {
        sendMessage(userId, String.format(BotCommand.START.getMessage(), userId));
    }

    private void processReadCommand(long userId) {
        sendMessage(userId, BotCommand.READ.getMessage());
    }

    private void processStopReadCommand(long userId) {
        sendMessage(userId, BotCommand.STOP_READ.getMessage());
    }

    private void processOtpAndRegisterUser(long userId, String otp) {
        if(!otpCache.containsKey(userId)) {
            sendMessage(userId, "Sorry, cannot process your OTP verification");
        }
        if(!otpCache.getValue(userId).equals(otp)) {
            sendMessage(userId, "Invalid OTP, please try again");
        }
        userService.saveUser(userId);
        sendMessage(userId, "OTP verification successful");
    }

    private void processMessage(long chatId, String messageText) {
        sendMessage(chatId, messageText);
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
