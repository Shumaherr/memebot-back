package com.robotyagi.photohackmeme.service;

import com.robotyagi.photohackmeme.controller.Bot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MessageService {


    public MessageService(Object s, Bot bot) {
    }

    public URL getPhotoFromMessage(Message message) throws MalformedURLException {
        URL url = new URL("");
        return url ;
    }

    public boolean sendMessage(Bot bot, String chatId, String photo, String text)
    {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(photo);
        sendPhotoRequest.setCaption(text);
        try {
            bot.sendPhoto(sendPhotoRequest);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendMessage(Bot bot, String chatId, String photo) {
        return sendMessage(bot, chatId, photo, "");
    }



}
