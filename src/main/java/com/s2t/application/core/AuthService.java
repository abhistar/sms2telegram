package com.s2t.application.core;

import com.s2t.application.bot.TelegramBot;
import com.s2t.application.model.Cache;
import com.s2t.application.model.UserEntity;
import com.s2t.application.model.dto.responses.OtpResponse;
import com.s2t.application.model.dto.responses.UserStatusResponse;
import com.s2t.application.model.enums.UserStatus;
import com.s2t.application.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.s2t.application.util.StringConstants.Message.CONFIRM_YOUR_OTP_HERE_WITH_OTP_COMMAND;
import static com.s2t.application.util.StringConstants.Message.USER_ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TelegramBot telegramBot;

    private final UserService userService;

    private final Cache<Long, String> otpCache;

    public OtpResponse getOTP(Long id) {
        String otpToken = AuthUtil.generateOTP();

        UserEntity user = userService.loadUserByUserId(id);
        if(Objects.nonNull(user)) {
            return OtpResponse.builder().message(USER_ALREADY_REGISTERED).build();
        }
        otpCache.addKey(id, otpToken);

        telegramBot.sendMessage(id,CONFIRM_YOUR_OTP_HERE_WITH_OTP_COMMAND);

        return OtpResponse.builder().otp(otpToken).message("token").build();
    }


    public UserStatusResponse getUserSignUpStatus(Long id) {
        UserEntity user = userService.loadUserByUserId(id);
        if(Objects.isNull(user)) {
            return UserStatusResponse.builder().userStatus(UserStatus.UNREGISTERED).build();
        }
        if(!user.getIsActive()) {
            return UserStatusResponse.builder().userStatus(UserStatus.INACTIVE).build();
        }
        return UserStatusResponse.builder().userStatus(UserStatus.ACTIVE).token(user.getPassword()).build();
    }
}
