package com.robotyagi.photohackmeme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class SearchService {
    String getUrl(double[] grade)
    {
        ArrayList allMemes = new ArrayList();

        String url = "jdbc:postgresql://20.188.36.158:5432/hacker";
        Properties props = new Properties();
        props.setProperty("user","hacker");
        props.setProperty("password","Hacker1!");
        props.setProperty("ssl","true");
        ArrayList<Double> result = new ArrayList<>(8);

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT anger, contempt, disgust, fear, happiness, neutral, sadness, surprise FROM hacker");

            while (rs.next()) {
                int i = 0;
                while(i <= 7) {
                    result.add(rs.getDouble(i++));
                }
            }
            //System.out.println(result.toString());
            rs.close();
            st.close();
        }
        catch(Exception e){
            System.out.println("DB error: "+ e );
        }


        double shortestDistance;
        double distance=0;

        System.out.println(result.get(0));



        return url;}
}

