package com.s2t.application.controller;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.dto.PingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {
    private final TelegramBot telegramBot;

    @Value("${telegram.bit.test.chat_id}")
    private String testChatId;

    @GetMapping("")
    public PingResponse getPingResponseInWebAndTelegram() {
        SendMessage message = SendMessage.builder().chatId(testChatId).text("PONG").build();
        telegramBot.sendMessage(message);

        return PingResponse.builder().reply("PONG").build();
    }

    @GetMapping("/{messageText}")
    public ResponseEntity<PingResponse> getCustomPingResponseInWebAndTelegram(@PathVariable String messageText) {
        SendMessage message = SendMessage.builder().chatId(testChatId).text(messageText).build();
        telegramBot.sendMessage(message);

        return new ResponseEntity<>(PingResponse.builder().reply(messageText).build(), HttpStatus.OK);
    }
}
