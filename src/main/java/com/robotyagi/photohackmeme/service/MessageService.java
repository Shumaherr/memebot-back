package com.robotyagi.photohackmeme.service;

import com.robotyagi.photohackmeme.controller.Bot;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MessageService {

    public String getPhotoFromMessage(String token, String fileId) throws MalformedURLException {
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
