package com.robotyagi.photohackmeme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

public class SearchService {
    String getUrl(double[] grade)
    {
        ArrayList allMemes = new ArrayList();

        String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("user","fred");
        props.setProperty("password","secret");
        props.setProperty("ssl","true");


        try{
            Connection conn = DriverManager.getConnection(url, props);
        }
        catch(Exception e){


        }






        return url;
    }
}
