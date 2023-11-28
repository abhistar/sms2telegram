package com.s2t.application.bot;

import com.s2t.application.core.NotificationService;
import com.s2t.application.core.UserService;
import com.s2t.application.model.Cache;
import com.s2t.application.util.StringConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.s2t.application.util.StringConstants.Message.COMMAND_UNKNOWN_MESSAGE;
import static com.s2t.application.util.StringConstants.Message.INVALID_OTP_PLEASE_TRY_AGAIN;
import static com.s2t.application.util.StringConstants.Message.OTP_VERIFICATION_SUCCESSFUL;
import static com.s2t.application.util.StringConstants.Message.SORRY_CANNOT_PROCESS_YOUR_OTP_VERIFICATION;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.username}")
    private String telegramBotUsername;

    private final Cache<Long, String> otpCache;

    private final UserService userService;

    private final NotificationService notificationService;

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

    @SneakyThrows
    public void sendMessage(long userId, String messageText) {
        SendMessage message = SendMessage.builder().chatId(userId).text(messageText).build();
        execute(message);
    }

    private void executeNextAction(long userId, String messageText) {
        if(messageText.startsWith("/")) {
            processCommand(userId, messageText.substring(1));
        } else {
            sendMessage(userId, messageText);
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
        /* TODO add method to enable reading sms from mobile app
             use Push Notification service to enable */
        notificationService.sendNotification(StringConstants.Message.READING_SMS_FROM_YOUR_DEVICE);
    }

    private void processStopReadCommand(long userId) {
        sendMessage(userId, BotCommand.STOP_READ.getMessage());
        /* TODO add method to disable reading sms from mobile app
             use Push Notification service to enable */
        notificationService.sendNotification(StringConstants.Message.STOPPED_READING_SMS_FROM_YOUR_DEVICE);
    }

    private void processOtpAndRegisterUser(long userId, String otp) {
        if(!otpCache.containsKey(userId)) {
            sendMessage(userId, SORRY_CANNOT_PROCESS_YOUR_OTP_VERIFICATION);
        }
        if(!otpCache.getValue(userId).equals(otp)) {
            sendMessage(userId, INVALID_OTP_PLEASE_TRY_AGAIN);
        }
        userService.saveUser(userId);
        sendMessage(userId, OTP_VERIFICATION_SUCCESSFUL);
    }
}
