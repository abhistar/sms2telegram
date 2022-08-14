package com.s2t.application.util;

import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@UtilityClass
public class AuthUtil {
    private static final int otpLength = 4;

    public static String generateOTP() {
        return generateOTP(otpLength);
    }

    public static String generateOTP(int otpLength) {
        StringBuilder otp = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        try {
            secureRandom = SecureRandom.getInstance(secureRandom.getAlgorithm());
            for (int i = 0; i < otpLength; i++) {
                otp.append(secureRandom.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return otp.toString();
    }
}
