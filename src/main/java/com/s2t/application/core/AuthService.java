package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.Cache;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.responses.OtpResponse;
import com.s2t.application.model.dto.responses.UserStatusResponse;
import com.s2t.application.model.enums.UserStatus;
import com.s2t.application.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final TelegramBot telegramBot;

    private final UserService userService;

    private final Cache<Long, String> otpCache;

    public OtpResponse getOTP(Long id) {
        String otpToken = AuthUtil.generateOTP();

        UserEntity user = userService.loadUserByUserId(id);
        if(Objects.nonNull(user) && user.getIsActive()) {
            return OtpResponse.builder().message("User already registered").build();
        }
        otpCache.addKey(id, otpToken);

        telegramBot.sendMessage(id,"Confirm your OTP here with /otp command");

        return OtpResponse.builder().otp(otpToken).message("token").build();
    }


    public UserStatusResponse getUserSignUpStatus(Long id) {
        UserEntity user = userService.loadUserByUserId(id);
        if(Objects.nonNull(user) && user.getIsActive()) {
            return UserStatusResponse.builder().userStatus(UserStatus.ACTIVE)
                    .token(user.getPassword()).build();
        }
        return UserStatusResponse.builder().userStatus(UserStatus.INACTIVE).build();
    }
}
