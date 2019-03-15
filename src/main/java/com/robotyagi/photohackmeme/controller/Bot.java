package com.robotyagi.photohackmeme.controller;

import com.microsoft.azure.storage.StorageException;
import com.robotyagi.photohackmeme.service.FileService;
import com.robotyagi.photohackmeme.service.MessageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Service
public class Bot extends TelegramLongPollingBot {

    @Autowired
    MessageService messageService;
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
                try {
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
                }
            }
        }
        if(message.hasPhoto())
        {
            for(PhotoSize o:message.getPhoto())
            {
                System.out.println(o.getFileId());

            }
            //TODO Перенести извлечение фото в MessageService. Файл не сохраняем, передаем в PicProcessor
            try {
                messageService = new MessageService();
                ArrayList<String> memesArr = FileService.uploadFile(fileOutName, message.getPhoto().get(message.getPhoto().size() - 1).getFileId(), this, message.getPhoto().size());
                String memes = memesArr.get(0);
                String text = memesArr.get(1);
                SendPhoto sendPhotoRequest = new SendPhoto();
                sendPhotoRequest.setChatId(message.getChatId().toString());
                sendPhotoRequest.setPhoto(memes);
                String result = Double.parseDouble(text) >=80.0d?"% Хороший результат!!111" : "% Так себе!(";
                sendPhotoRequest.setCaption("Вы меметичны на " + text + result);
                try {
                    sendPhoto(sendPhotoRequest);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (StorageException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
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