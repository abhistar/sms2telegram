package com.s2t.application.controller;

import com.s2t.application.core.AuthService;
import com.s2t.application.model.dto.responses.OtpResponse;
import com.s2t.application.model.dto.responses.UserStatusResponse;
import com.s2t.application.model.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @Test
    void testGetOTP() throws Exception {
        OtpResponse response = OtpResponse.builder().otp("123456").message("token").build();
        when(authService.getOTP(anyLong())).thenReturn(response);

        mockMvc.perform(get("/auth/1/otp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("token"))
                .andExpect(jsonPath("$.otp").value("123456"));

        verify(authService).getOTP(1L);
    }

    @Test
    void testGetUserSignUpStatus() throws Exception {
        UserStatusResponse response = UserStatusResponse.builder().userStatus(UserStatus.ACTIVE).token("token").build();
        when(authService.getUserSignUpStatus(anyLong())).thenReturn(response);

        mockMvc.perform(get("/auth/1/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.token").value("token"));

        verify(authService).getUserSignUpStatus(1L);
    }
}