package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.PingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Controller
@RequiredArgsConstructor
public class PingController {
    private final TelegramBot telegramBot;

    @GetMapping("/ping/{messageText}")
    public ResponseEntity<PingResponse> getPingResponse(@PathVariable String messageText) {
        SendMessage message = SendMessage.builder().chatId("936847251").text(messageText).build();
        telegramBot.sendMessage(message);

        return new ResponseEntity<>(PingResponse.builder().reply("PONG").build(), HttpStatus.OK);
    }
}
