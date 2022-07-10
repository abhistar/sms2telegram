package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TelegramBot telegramBot;

    public AuthRegisterResponse registerUser(String id) {
        String OtpToken = AuthUtil.generateOTP();
        String token = "token";

        telegramBot.sendMessage(SendMessage.builder().chatId(id).text("Confirm your OTP here").build());

        return AuthRegisterResponse.builder().otp(OtpToken).token(token).build();
    }
}
