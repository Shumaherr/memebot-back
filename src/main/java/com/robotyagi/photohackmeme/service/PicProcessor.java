package com.robotyagi.photohackmeme.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.robotyagi.photohackmeme.model.Memes;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class PicProcessor {

    @Autowired
    private SearchService searchService;

    //@Value("${microsoft.api.cognitive.key}")
    private String apiKey = "49ede4b965594f68a165b726a5d7d5e6";

    private Memes getEmotions(String pictureurl) {
        final String body = "{\"url\": \"" + pictureurl + "\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Memes selfie = new Memes();
            String response = new String();
            JSONObject emo = new JSONObject();
            try {
                URL url = new URL("https://francecentral.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=false&returnFaceLandmarks=false&returnFaceAttributes=emotion");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(15000);
                con.setConnectTimeout(15000);
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);

                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(body);

                writer.flush();
                writer.close();
                os.close();
                int responseCode = con.getResponseCode();
                System.out.print(responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    response = br.readLine();
                    emo = new JSONArray(response).getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion");
                }
                else {
                    emo = new JSONObject("{\"Error\" : \"Error Registering\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try
            {
                selfie = objectMapper.readValue(emo.toString(), Memes.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return selfie;
    }

    public String getResultImage(String pictureurl) {
        String imageString = new String();
        Memes emotions = getEmotions(pictureurl);
        String template = searchService.getNearestMeme(emotions);
        imageString = getPicAPI(pictureurl, template);

        return imageString;
    }

    public String getPicAPI(String picURL, String template){
        String newPicUrl =new String();
        try {
            HttpResponse<String> response = Unirest.post("http://api-soft.photolab.me/template_process.php")
                    .field("image_url[1]", picURL)
                    .field("template_name", template)
                    //.body("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"image_url[1]\"\r\n\r\n"+picURL+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"template_name\"\r\n\r\n"+template+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--")
                    .asString();
            newPicUrl = response.getBody();
        }
        catch(Exception e){e.printStackTrace(); }
        return newPicUrl;
    }

}

