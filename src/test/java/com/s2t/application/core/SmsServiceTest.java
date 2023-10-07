package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.requests.SmsRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SmsServiceTest {
    @Mock
    UserService userService;

    @Mock
    TelegramBot bot;

    @InjectMocks
    SmsService smsService;

    @Captor
    ArgumentCaptor<Long> userIdCaptor;

    @Captor
    ArgumentCaptor<String> messageCaptor;

    @Nested
    class sendSmsToTelegramMethod {
        @Test
        void sendMessageToBot_WhenUserIsPresentAndActive() {
            UserEntity user = Mockito.mock(UserEntity.class);
            when(user.getIsActive()).thenReturn(true);
            when(userService.loadUserByUserId(anyLong())).thenReturn(user);
            doNothing().when(bot).sendMessage(anyLong(), any());

            SmsRequest request = SmsRequest.builder().message("Message").build();
            smsService.sendSmsToTelegram(1L, request);

            verify(bot, times(1)).sendMessage(userIdCaptor.capture(), messageCaptor.capture());
            assertEquals(1L, userIdCaptor.getValue());
            assertEquals("Message", messageCaptor.getValue());
        }

        @Test
        void doNotSendMessageToBot_WhenUserIsPresentAndButInactive() {
            UserEntity user = Mockito.mock(UserEntity.class);
            when(user.getIsActive()).thenReturn(false);
            when(userService.loadUserByUserId(anyLong())).thenReturn(user);

            SmsRequest request = SmsRequest.builder().message("Message").build();
            smsService.sendSmsToTelegram(1L, request);

            verify(bot, times(0)).sendMessage(anyLong(), any());
        }

        @Test
        void throwIllegalArgumentException_WhenInvalidRequest() {
            SmsRequest request = Mockito.mock(SmsRequest.class);
            when(request.getMessage()).thenReturn(null);

            assertThrows(IllegalArgumentException.class, () -> smsService.sendSmsToTelegram(null, request));
            assertThrows(IllegalArgumentException.class, () -> smsService.sendSmsToTelegram(1L, null));
            assertThrows(IllegalArgumentException.class, () -> smsService.sendSmsToTelegram(1L, request));
        }

        @Test
        void throwNullPointerException_WhenUserNotRegistered() {
            when(userService.loadUserByUserId(anyLong())).thenReturn(null);

            SmsRequest request = SmsRequest.builder().message("Message").build();

            assertThrows(NullPointerException.class, () -> smsService.sendSmsToTelegram(1L, request));
        }
    }
}