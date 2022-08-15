package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.dto.AuthRegisterResponse;
import com.s2t.application.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TelegramBot telegramBot;

    public AuthRegisterResponse registerUser(Long id) {
        String OtpToken = AuthUtil.generateOTP();
        String token = "token";

        telegramBot.sendMessage(id,"Confirm your OTP here");

        return AuthRegisterResponse.builder().otp(OtpToken).token(token).build();
    }
}
