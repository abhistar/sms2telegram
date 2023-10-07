package com.s2t.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s2t.application.core.SmsService;
import com.s2t.application.model.dto.requests.SmsRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = SmsController.class)
@AutoConfigureMockMvc(addFilters = false)
class SmsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SmsService smsService;

    @Captor
    ArgumentCaptor<SmsRequest> argumentCaptor;

    @Test
    void getOkStatus_WhenValidRequest() throws Exception {
        doNothing().when(smsService).sendSmsToTelegram(anyLong(), any());

        ObjectMapper objectMapper = new ObjectMapper();
        SmsRequest smsRequest = new SmsRequest("test message");
        String request = objectMapper.writeValueAsString(smsRequest);

        mockMvc.perform(post("/sms/1/send")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf8").content(request))
                .andExpect(status().isOk());

        verify(smsService).sendSmsToTelegram(eq(1L), argumentCaptor.capture());
        assertEquals("test message", argumentCaptor.getValue().getMessage());
    }

    @Test
    void getBadRequestStatus_WhenMissingRequiredArgs() throws Exception {
        doThrow(IllegalArgumentException.class).when(smsService).sendSmsToTelegram(anyLong(), any());

        mockMvc.perform(post("/sms/1/send"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getInternalServerErrorStatus_WhenAnyOtherException() throws Exception {
        doThrow(NullPointerException.class).when(smsService).sendSmsToTelegram(anyLong(), any());

        ObjectMapper objectMapper = new ObjectMapper();
        SmsRequest smsRequest = new SmsRequest("test message");
        String request = objectMapper.writeValueAsString(smsRequest);

        mockMvc.perform(post("/sms/1/send")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf8").content(request))
                .andExpect(status().isInternalServerError());
    }
}