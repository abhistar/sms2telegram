package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.requests.SmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final UserService userService;

    private final TelegramBot telegramBot;

    public void sendSmsToTelegram(Long userId, SmsRequest request) {
        if(Objects.isNull(userId) || Objects.isNull(request) || Objects.isNull(request.getMessage())) {
            throw new IllegalArgumentException();
        }
        UserEntity user = userService.loadUserByUserId(userId);
        if(user.getIsActive()) {
            telegramBot.sendMessage(userId, request.getMessage());
        }
    }
}
