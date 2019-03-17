package com.robotyagi.photohackmeme.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.robotyagi.photohackmeme.model.Memes;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
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

    public ArrayList<String> getTemplateByKeywords(String text) {
        ArrayList<String> returnList = new ArrayList<String>();
        String[] words = text.replaceAll("\\.|!|,|\\?", " " ).split("\\s+");
        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, words);
        String mergedText = new String();

        for(int i = 0; i < wordList.size(); i++)
        {
            if(wordList.get(i).length() < 3)
            {
                wordList.remove(i);
            }

        }
        for(int i = 0; i < wordList.size(); i++)
        {
            wordList.set(i, wordList.get(i).concat(":*"));
            wordList.set(i, wordList.get(i).toLowerCase());
        }
        mergedText = String.join(" | ", wordList);
        String query = "select url " +
                "from memelib " +
                "where description_vect @@ to_tsquery('" + mergedText + "')";

        Properties props = new Properties();
        props.setProperty("user", "postgreadmin");
        props.setProperty("password", "memebotdb");
        props.setProperty("ssl", "false");
        List<Memes> allMemes = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://18.219.76.113:5432/memebot", props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
                returnList.add(rs.getString("url"));
            rs.close();
            st.close();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnList;
    }
}
