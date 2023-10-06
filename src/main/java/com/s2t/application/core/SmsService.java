package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.requests.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {
    private final UserService userService;

    private final TelegramBot telegramBot;

    public void sendSmsToTelegram(long userId, SmsRequest request) {
        try {
            UserEntity user = userService.loadUserByUserId(userId);
            if(user.getIsActive()) {
                telegramBot.sendMessage(userId, request.getMessage());
            }
        }
        catch (Exception e) {
            log.error("ERROR: ", e);
            throw new RuntimeException();
        }
    }
}
