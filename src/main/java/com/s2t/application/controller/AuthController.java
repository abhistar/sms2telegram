package com.s2t.application.controller;

import com.s2t.application.model.dto.AuthRegisterResponse;
import com.s2t.application.core.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/{id}")
    public AuthRegisterResponse registerTelegramId(@PathVariable Long id) {
        return authService.registerUser(id);
    }
}
