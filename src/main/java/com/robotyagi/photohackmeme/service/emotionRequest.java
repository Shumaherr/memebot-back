package com.robotyagi.photohackmeme.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class emotionRequest {

    String getEmotions(String pictureurl)
    {
        final String body= "{\"url\": \""+pictureurl+"\"}";
            try{
                URL url = new URL("https://francecentral.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=false&returnFaceLandmarks=false&returnFaceAttributes=emotion");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Ocp-Apim-Subscription-Key", "fd3c1e04a97947a4acf0d88024b1b518");
                con.setDoOutput(true);
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
}
