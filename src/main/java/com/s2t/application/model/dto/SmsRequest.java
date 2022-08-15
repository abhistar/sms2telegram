package com.s2t.application.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsRequest {
    private Long chatId;
    private String smsMessage;
}
