package com.s2t.application.controller;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.core.UserService;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {
    private final UserService userService;

    private final TelegramBot telegramBot;

    @PostMapping("/send")
    public Object sendSmsToTelegram(@RequestBody SmsRequest smsRequest) {
        if (Objects.isNull(smsRequest) || Objects.isNull(smsRequest.getChatId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.loadUserByChatId(smsRequest.getChatId());
        telegramBot.sendMessage(smsRequest.getChatId(), smsRequest.getSmsMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
