package com.robotyagi.photohackmeme.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class TextProcessor {

    public String getTextEmotion(String Text)
    {
        //String Text = "I love to kill puppies";
        String api_key = "r6PrA2wsTA0UnidooshwyQ3Tl1bY0FuvAj0LqXPJIRM";
        String output = "";
        try {
            HttpResponse<String> response = Unirest.post("https://apis.paralleldots.com/v3/emotion")
                    //.header("Content-Type", "form-data")
                    //.body("----WebKitFormBoundary7MA4YWxkTrZu0gW\r\ncache-control: no-cache\r\nPostman-Token: 6543134b-3461-4111-8ba7-36176810675a\r\n\r\nContent-Disposition: form-data; name=\"api_key\"\r\n\r\n"+api_key+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"text\"\r\n\r\n"+Text+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--")
                    //.body("{\"api_key\":\""+api_key+"\", \"text\":\""+Text+"\"}")
                    .field("text",Text)
                    .field("api_key",api_key)
                    .asString();
            output = response.getBody();
        }
        catch(Exception e){System.out.println(e); }

//        System.out.println(output);
        return output;
    }
}
