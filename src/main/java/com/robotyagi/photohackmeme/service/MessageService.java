package com.robotyagi.photohackmeme.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Configurable
@Service
public class MessageService {
    @Autowired
    PicProcessor picProcessor;
    @Autowired
    TextProcessor textProcessor;

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
    /*public Vector<String> getMessageResponse(String token, String fileId) {
        Vector<String> response = new Vector<>();
        response.add(getPhotoResponse(token, fileId));

        return response;
    }*/

    public JSONArray getMessageResponse(String photoUrl, String text)
    {
        JSONArray response = new JSONArray();
        String template = new String();
        JSONObject textEmoJson = new JSONObject(textProcessor.getTextEmotion(text));
        ArrayList<String> textTemplates = textProcessor.getTemplateByKeywords(text);
        if(textTemplates.isEmpty()) {
            switch (textEmoJson.getJSONObject("emotion").getString("emotion")) {
                case "Happy":
                    template = "10000160"; //ГОВНО
                    break;
                case "Fear":
                    template = "10000166";
                    break;
                case "Sad":
                    template = "10000127";
                    break;
                case "Angry":
                    template = "10000163";
                    break;
                case "Bored":
                    template = "0";
                    break;
                case "Excited":
                    template = "10000160";
                    break;
                default:
                    template = "0";
            }
            if (!template.equals("0")) {
                photoUrl = picProcessor.getPicAPI(photoUrl, template);
            }
            response.put(picProcessor.getResultImage(photoUrl));
        }
        else
            for(String url : textTemplates)
            {
                response.put(picProcessor.getPicAPI(photoUrl, url));
            }
        return response;
    }

}
