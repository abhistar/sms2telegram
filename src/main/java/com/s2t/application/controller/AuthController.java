package com.s2t.application.controller;

import com.s2t.application.model.dto.responses.OtpResponse;
import com.s2t.application.core.AuthService;
import com.s2t.application.model.dto.responses.UserStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/{id}/otp")
    public OtpResponse getOTP(@PathVariable Long id) {
        return authService.getOTP(id);
    }

    @GetMapping("/{id}/status")
    public UserStatusResponse getUserSignUpStatus(@PathVariable Long id) {
        return authService.getUserSignUpStatus(id);
    }
}
