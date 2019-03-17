package com.robotyagi.photohackmeme.controller;

import com.robotyagi.photohackmeme.service.MessageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
            try {
                PhotoSize o = message.getPhoto().get(0);
            MessageService messageService = new MessageService();
                System.out.print(messageService);
            Vector<String> result = messageService.getMessageResponse(getPhotoFromMessage(this.getBotToken(), o.getFileId()), message.getText());
                SendPhoto sendPhotoRequest = new SendPhoto();
                sendPhotoRequest.setChatId(message.getChatId().toString());
                sendPhotoRequest.setPhoto(result.get(0));

                System.out.print(result.get(0));



                    sendPhoto(sendPhotoRequest);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
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

    private String getPhotoFromMessage(String token, String fileId) throws MalformedURLException {
        URL url = new URL("https://api.telegram.org/bot"+token+"/getFile?file_id="+fileId);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String res = in.readLine();
            JSONObject jresult = new JSONObject(res);
            String full_path = "https://api.telegram.org/file/bot" + token + "/" + jresult.getJSONObject("result").getString("file_path");
            return full_path;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostConstruct
    public void registerBot(){

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}