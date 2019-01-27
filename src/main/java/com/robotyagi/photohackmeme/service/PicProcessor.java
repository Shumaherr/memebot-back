package com.robotyagi.photohackmeme.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;


import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public double[] getArray(String jsonin) {
        String objin = jsonin.substring(1, jsonin.length() - 1);
        JSONObject obj = new JSONObject(objin).getJSONObject("faceAttributes");
        JSONObject emotion = obj.getJSONObject("emotion");
        double anger = emotion.getDouble("anger");
        double contempt = emotion.getDouble("contempt");
        double disgust = emotion.getDouble("disgust");
        double fear = emotion.getDouble("fear");
        double happiness = emotion.getDouble("happiness");
        double neutral = emotion.getDouble("neutral");
        double sadness = emotion.getDouble("sadness");
        double surprise = emotion.getDouble("surprise");

        double[] grade = new double[8];
        grade[0] = anger;
        grade[1] = contempt;
        grade[2] = disgust;
        grade[3] = fear;
        grade[4] = happiness;
        grade[5] = neutral;
        grade[6] = sadness;
        grade[7] = surprise;
        return grade;
    }
    public ArrayList<String> processImage(String inputImageUrl) {
        String emo = this.getEmotions(inputImageUrl);
        double[] emotions = this.getArray(emo);
        SearchService ss = new SearchService();
        ArrayList<String> returnlist = ss.getUrl(emotions);
        String memeOutUrl = this.getPicAPI(inputImageUrl, returnlist.get(0));
        returnlist.set(0,memeOutUrl);
        return returnlist;
    }
    public String getPicAPI(String picURL, String template){
        String newPicUrl ="";
        try {
            HttpResponse<String> response = Unirest.post("http://api-soft.photolab.me/template_process.php")
                    .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                    .header("cache-control", "no-cache")
                    .header("Postman-Token", "9e3972c7-f1da-412f-a9f2-8bf332ae2d71")
                    .body("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"image_url[1]\"\r\n\r\n"+picURL+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"template_name\"\r\n\r\n"+template+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--")
                    .asString();
            newPicUrl = response.getBody();
            }
        catch(Exception e){System.out.println(e); }
        return newPicUrl;
    }

    public OutputStream getCert (int index, InputStream is)
    {
        InputStream ismain = is;
        BufferedImage read = null;
        try {
            read = ImageIO.read(ismain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = read.getGraphics();
        g.setColor(Color.BLACK);
        g.drawString("Hello world ",7, 55);
        g.dispose();
        OutputStream os = null;
        try {
            ImageIO.write(read, "jpg", new File("image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }
}

