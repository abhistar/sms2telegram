package com.s2t.application.controller;

import com.s2t.application.bot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.s2t.application.util.StringConstants.PONG;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = PingController.class)
@AutoConfigureMockMvc(addFilters=false)
public class PingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TelegramBot telegramBot;

    @Value("${telegram.bot.test.chat_id}")
    Long testChatId;

    @Test
    public void testGetPingResponseInWebAndTelegram() throws Exception {
        doNothing().when(telegramBot).sendMessage(anyLong(), anyString());

        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(PONG));

        verify(telegramBot).sendMessage(testChatId, PONG);
    }

    @Test
    public void testGetCustomPingResponseInWebAndTelegram() throws Exception {
        String messageText = "CustomMessage";

        doNothing().when(telegramBot).sendMessage(anyLong(), anyString());

        mockMvc.perform(get("/ping/" + messageText))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(messageText));

        verify(telegramBot).sendMessage(testChatId, messageText);
    }
}
