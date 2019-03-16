package com.robotyagi.photohackmeme.service;

import com.robotyagi.photohackmeme.controller.Bot;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

@Configurable
@Service
public class MessageService {

    private PicProcessor picProcessor = new PicProcessor();

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

    private String getPhotoResponse(String token, String fileId) {
        String photoUrl = new String();
        try {
            photoUrl = getPhotoFromMessage(token, fileId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return picProcessor.getResultImage(photoUrl);
    }
    public Vector<String> getMessageResponse(String token, String fileId) {
        Vector<String> response = new Vector<>();
        response.add(getPhotoResponse(token, fileId));

        return response;
    }
}
