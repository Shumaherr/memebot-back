package com.robotyagi.photohackmeme;


import com.robotyagi.photohackmeme.model.Bot;
import com.robotyagi.photohackmeme.service.PicProcessor;
import com.robotyagi.photohackmeme.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Arrays;

@SpringBootApplication
public class PhotohackMemeApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		SpringApplication.run(PhotohackMemeApplication.class, args);
	}

}

