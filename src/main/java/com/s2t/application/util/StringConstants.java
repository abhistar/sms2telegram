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
    }


}
