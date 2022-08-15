package com.s2t.application;

import com.s2t.application.bot.BotContextHandler;
import com.s2t.application.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
@ComponentScan(basePackages = {"com.s2t.application"})
public class S2tApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(S2tApplication.class, args);
		try {
			BotContextHandler contextHandler = new BotContextHandler(applicationContext.getBean(TelegramBot.class));
			contextHandler.handleContext();
		} catch (TelegramApiException e) {
			e.printStackTrace();

		}
	}

}
