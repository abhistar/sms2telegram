package com.s2t.application.controller;

import com.s2t.application.core.SmsService;
import com.s2t.application.model.dto.requests.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/{id}/send")
    public Object sendSmsToTelegram(@PathVariable Long id, @RequestBody SmsRequest smsRequest) {
        if (Objects.isNull(id) || Objects.isNull(smsRequest) || Objects.isNull(smsRequest.getMessage())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        smsService.sendSmsToTelegram(id, smsRequest);

        return new ResponseEntity<>(Map.of("status", "Success"), HttpStatus.OK);
    }
}
