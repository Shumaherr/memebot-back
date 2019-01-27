package com.robotyagi.photohackmeme.service;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PicProcessor {

    String getEmotions(String pictureurl)
    {
        //final String body= "{\"url\": \""+pictureurl+"\"}";
        final String body= "{\"url\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/800px-Donald_Trump_official_portrait.jpg\"}";
        System.out.println(body);
        try{
                URL url = new URL("https://francecentral.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=false&returnFaceLandmarks=false&returnFaceAttributes=emotion");
                HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Ocp-Apim-Subscription-Key", "fd3c1e04a97947a4acf0d88024b1b518");
                con.setDoOutput(true);
                con.setDoInput(true);

            OutputStream os = con.getOutputStream();

                InputStream is = new ByteArrayInputStream(body.getBytes());
                int c;
                byte[] buf = new byte[8192];
                while ((c = is.read(buf, 0, buf.length)) > 0)
                {
                    os.write(buf, 0, c);
                    os.flush();
                }
                os.close();
                is.close();
                Object response = con.getContent();
                return response.toString();
            } catch (Exception e)
            {
                e.printStackTrace();
                return e.toString();
            }

            }

    public double[] getArray(String jsonin)
    {
        String url = jsonin.substring(1,jsonin.length()-1);
        JSONObject obj = new JSONObject(jsonin).getJSONObject("faceAttributes");

        JSONArray arr = obj.getJSONArray("emotion");

        double[] grade= new double[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            double elem = arr.getDouble(i);
            grade[i] = elem;
        }

        return grade;
    }
}
