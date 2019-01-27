package com.robotyagi.photohackmeme.service;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PicProcessor {

    public String getEmotions(String pictureurl) {
        final String body = "{\"url\": \"" + pictureurl + "\"}";

            String response = "";
            try {
                URL url = new URL("https://francecentral.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=false&returnFaceLandmarks=false&returnFaceAttributes=emotion");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(15000);
                con.setConnectTimeout(15000);
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Ocp-Apim-Subscription-Key", "fd3c1e04a97947a4acf0d88024b1b518");

                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(body);

                writer.flush();
                writer.close();
                os.close();
                int responseCode = con.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    response = br.readLine();

                } else {
                    response = "Error Registering";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
    }

    public double[] getArray(String jsonin)
    {
        String objin = jsonin.substring(1,jsonin.length()-1);
        JSONObject obj = new JSONObject(objin).getJSONObject("faceAttributes");
        JSONObject emotion = obj.getJSONObject("emotion");
        System.out.println(emotion);
        double anger = emotion.getDouble("anger");
        double contempt = emotion.getDouble("contempt");
        double disgust = emotion.getDouble("disgust");
        double fear = emotion.getDouble("fear");
        double happiness = emotion.getDouble("happiness");
        double neutral = emotion.getDouble("neutral");
        double sadness = emotion.getDouble("sadness");
        double surprise = emotion.getDouble("surprise");

        double[] grade = new double[8];
        grade[0]= anger;
        grade[1]= contempt;
        grade[2]= disgust;
        grade[3]= fear;
        grade[4]= happiness;
        grade[5]= neutral;
        grade[6]= sadness;
        grade[7]= surprise;
        return grade;
    }
}
