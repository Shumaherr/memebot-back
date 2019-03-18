package com.robotyagi.photohackmeme;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@EnableJpaRepositories
@SpringBootApplication
public class PhotohackMemeApplication {

	public static void main(String[] args) {
		/*ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}*/
		SpringApplication.run(PhotohackMemeApplication.class, args);
	}

}

