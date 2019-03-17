package com.robotyagi.photohackmeme.controller;

import com.robotyagi.photohackmeme.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;

@Configurable
@Component
public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "memeficator_bot";
    }

    @Override
    public String getBotToken() {
        return "758153614:AAGmxQgNclxVgJV-lrs1pbHuNDQs8-J_0Ro";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help"))
                sendMsg(message, "Привет, я подберу мем, отражающий твои эмоции. Отправь мне фото");
            else
            {
                SendPhoto sendPhotoRequest = new SendPhoto();
                sendPhotoRequest.setChatId(message.getChatId().toString());
               /* try {
                    sendPhotoRequest.setPhoto(FileService.getStaticImage("risovach.ru.png"));
                    sendPhoto(sendPhotoRequest);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (StorageException e) {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }*/
            }
        }
        if(message.hasPhoto())
        {
            MessageService messageService = new MessageService();
                System.out.print(messageService);
                Vector<String> result = messageService.getMessageResponse(this.getBotToken(), message.getPhoto().get(message.getPhoto().size() - 1).getFileId());
                SendPhoto sendPhotoRequest = new SendPhoto();
                sendPhotoRequest.setChatId(message.getChatId().toString());
                sendPhotoRequest.setPhoto(result.get(0));

                System.out.print(result.get(0));


                try {
                    sendPhoto(sendPhotoRequest);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



}