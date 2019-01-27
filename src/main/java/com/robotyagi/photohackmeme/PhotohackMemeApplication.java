package com.robotyagi.photohackmeme;

import com.robotyagi.photohackmeme.config.BotInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhotohackMemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {PhotohackMemeApplication.class, BotInit.class}, args);
	}

}

