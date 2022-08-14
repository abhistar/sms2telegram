package com.s2t.application.controller;

import com.s2t.application.model.dto.AuthRegisterResponse;
import com.s2t.application.core.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/{id}")
    public ResponseEntity<AuthRegisterResponse> registerTelegramId(@PathVariable String id) {
        return new ResponseEntity<>(authService.registerUser(id), HttpStatus.OK);
    }
}
