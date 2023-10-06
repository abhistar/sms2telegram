package com.s2t.application.bot;

import com.s2t.application.util.StringConstants.Command;
import com.s2t.application.util.StringConstants.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BotCommand {
    START(Command.START, Message.WELCOME_MESSAGE),
    READ(Command.READ, Message.READING_SMS_MESSAGE),
    STOP_READ(Command.STOP_READ, Message.STOP_READING_SMS_MESSAGE),
    OTP(Command.OTP, Message.PROCESSING_OTP);

    private final String command;
    private final String message;

    public static BotCommand getByCommand(String command) {
        return Arrays.stream(BotCommand.values())
                .filter(value -> value.command.equals(command))
                .findFirst()
                .orElse(null);
    }
}
