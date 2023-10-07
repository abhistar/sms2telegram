package com.s2t.application.controller;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.dto.responses.PingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.s2t.application.util.StringConstants.PONG;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {
    private final TelegramBot telegramBot;

    @Value("${telegram.bot.test.chat_id}")
    private Long testChatId;

    @GetMapping("")
    public PingResponse getPingResponseInWebAndTelegram() {
        telegramBot.sendMessage(testChatId, PONG);

        return PingResponse.builder().message(PONG).build();
    }

    @GetMapping("/{messageText}")
    public PingResponse getCustomPingResponseInWebAndTelegram(@PathVariable String messageText) {
        telegramBot.sendMessage(testChatId, messageText);

        return PingResponse.builder().message(messageText).build();
    }
}
