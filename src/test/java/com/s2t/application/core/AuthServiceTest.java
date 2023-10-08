package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.Cache;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.responses.OtpResponse;
import com.s2t.application.model.dto.responses.UserStatusResponse;
import com.s2t.application.model.enums.UserStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.s2t.application.util.StringConstants.Message.USER_ALREADY_REGISTERED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Mock
    TelegramBot bot;

    @Mock
    UserService userService;

    @Mock
    Cache<Long, String> otpCache;

    @InjectMocks
    AuthService authService;

    @Nested
    class getOTPMethod {
        @Test
        void shouldReturnValidOTP_WhenSigningInNewUser() {
            when(userService.loadUserByUserId(anyLong())).thenReturn(null);
            doNothing().when(otpCache).addKey(any(), any());
            doNothing().when(bot).sendMessage(anyLong(), any());

            OtpResponse response = authService.getOTP(anyLong());

            assertEquals("token", response.getMessage());
            assertNotNull(response.getOtp());
        }

        @Test
        void shouldNotReturnOTP_WhenExistingUser() {
            when(userService.loadUserByUserId(anyLong())).thenReturn(Mockito.mock(UserEntity.class));

            OtpResponse response = authService.getOTP(anyLong());

            verifyNoInteractions(otpCache);
            verifyNoInteractions(bot);
            assertEquals(USER_ALREADY_REGISTERED, response.getMessage());
            assertNull(response.getOtp());
        }
    }

    @Nested
    class getUserSignUpStatusMethod {
        @Test
        void shouldReturnUnregisteredUserStatus_WhenNewUser() {
            when(userService.loadUserByUserId(anyLong())).thenReturn(null);

            UserStatusResponse response = authService.getUserSignUpStatus(anyLong());

            verify(userService, times(1)).loadUserByUserId(anyLong());
            assertEquals(UserStatus.UNREGISTERED, response.getUserStatus());
        }

        @Test
        void shouldReturnInactiveUserStatus_WhenExistingUserIsNotActive() {
            UserEntity user = Mockito.mock(UserEntity.class);
            when(user.getIsActive()).thenReturn(false);
            when(userService.loadUserByUserId(anyLong())).thenReturn(user);

            UserStatusResponse response = authService.getUserSignUpStatus(anyLong());

            verify(userService, times(1)).loadUserByUserId(anyLong());
            assertEquals(UserStatus.INACTIVE, response.getUserStatus());
        }

        @Test
        void shouldReturnInactiveUserStatus_WhenExistingUserIsActive() {
            UserEntity user = Mockito.mock(UserEntity.class);
            when(user.getIsActive()).thenReturn(true);
            when(userService.loadUserByUserId(anyLong())).thenReturn(user);

            UserStatusResponse response = authService.getUserSignUpStatus(anyLong());

            verify(userService, times(1)).loadUserByUserId(anyLong());
            assertEquals(UserStatus.ACTIVE, response.getUserStatus());
        }
    }
}