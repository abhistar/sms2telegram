package com.s2t.application.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRegisterResponse {
    private String otp;
    private String token;
}
