package com.robotyagi.photohackmeme.config;

import com.robotyagi.photohackmeme.model.Bot;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class BotConfig {
    @Configuration
    public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

        @Override
        public void onApplicationEvent(final ContextRefreshedEvent event) {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(new Bot());
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        }
    }
}
