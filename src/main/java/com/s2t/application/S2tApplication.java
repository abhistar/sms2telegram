package com.s2t.application;

import com.s2t.application.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@ComponentScan(basePackages = {"com.s2t.application"})
public class S2tApplication {
	public static void main(String[] args) {
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new TelegramBot());

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		SpringApplication.run(S2tApplication.class, args);
	}

}
