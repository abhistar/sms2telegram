package com.s2t.application.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringConstants {

    public static final String PONG = "pong";
    public static class Command {

        public static final String START = "start";

        public static final String READ = "read";

        public static final String STOP_READ = "stop_read";

        public static final String OTP = "otp";
    }

    public static class Message {
        public static final String WELCOME_MESSAGE = "Welcome to SMS to Telegram ChatBot! Your id is %d to enter in Sms2Telegram app and receive OTP";

        public static final String READING_SMS_MESSAGE = "Okay, reading you SMS from other device";

        public static final String STOP_READING_SMS_MESSAGE = "Okay stopping reading of sms";

        public static final String PROCESSING_OTP = "Processing OTP";

        public static final String COMMAND_UNKNOWN_MESSAGE = "Command unknown, use menu to see list of commands";

        public static final String SORRY_CANNOT_PROCESS_YOUR_OTP_VERIFICATION = "Sorry, cannot process your OTP verification";

        public static final String INVALID_OTP_PLEASE_TRY_AGAIN = "Invalid OTP, please try again";

        public static final String OTP_VERIFICATION_SUCCESSFUL = "OTP verification successful";

        public static final String USER_ALREADY_REGISTERED = "User already registered";

        public static final String CONFIRM_YOUR_OTP_HERE_WITH_OTP_COMMAND = "Confirm your OTP here with /otp command. Your OTP is valid for 2 minutes";

        public static final String READING_SMS_FROM_YOUR_DEVICE = "Reading sms from your device";

        public static final String STOPPED_READING_SMS_FROM_YOUR_DEVICE = "Stopped reading sms from your device";
    }

    public static class ExceptionMessage {

        public static final String ERROR = "ERROR: ";
        public static final String USERNAME_NOT_FOUND = "Username not found";
    }


}
