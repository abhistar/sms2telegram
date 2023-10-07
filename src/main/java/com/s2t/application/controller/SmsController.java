package com.s2t.application.controller;

import com.s2t.application.core.SmsService;
import com.s2t.application.model.dto.requests.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/{id}/send")
    public Object sendSmsToTelegram(@PathVariable Long id, @RequestBody SmsRequest smsRequest) {
        try {
            smsService.sendSmsToTelegram(id, smsRequest);
        }
        catch (IllegalArgumentException i) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
