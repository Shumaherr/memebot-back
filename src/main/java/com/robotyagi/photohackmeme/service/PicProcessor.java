package com.robotyagi.photohackmeme.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

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

    public String getPicAPI(String picURL, String template){
        String newPicUrl ="";
        final String body = "";
        try {
            HttpURLConnection connection = null;
            URL url = new URL("http://api-soft.photolab.me/template_process.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
   //         urlParameters.add(new BasicNameValuePair("image_url[1]", picURL));
   //         urlParameters.add(new BasicNameValuePair("template_name", template));
            DataOutputStream os = new DataOutputStream (
                    connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write("");
            os.close();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            newPicUrl=response.toString();
            br.close();
            }
        catch(Exception e){System.out.println(e); }
        return newPicUrl;
    }
}

