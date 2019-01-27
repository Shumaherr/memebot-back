package com.robotyagi.photohackmeme.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SearchService {
    public ArrayList<String> getUrl(double[] grade) {
        ArrayList allMemes = new ArrayList();

        String url = "jdbc:postgresql://20.188.36.158:5432/hacker";
        Properties props = new Properties();
        props.setProperty("user", "hacker");
        props.setProperty("password", "Hacker1!");
        props.setProperty("ssl", "false");
        ArrayList<List> DBList = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT anger, contempt, disgust, fear, happiness, neutral, sadness, surprise FROM memelib");
            try {

                while(true)
                {
                    if (rs.next()) { ArrayList<Double> list = new ArrayList<>();
                    list.add(0, rs.getDouble("anger"));
                    list.add(1, rs.getDouble("contempt"));
                    list.add(2, rs.getDouble("disgust"));
                    list.add(3, rs.getDouble("fear"));
                    list.add(4, rs.getDouble("happiness"));
                    list.add(5, rs.getDouble("neutral"));
                    list.add(6, rs.getDouble("sadness"));
                    list.add(7, rs.getDouble("surprise"));
                    DBList.add(list);
                    }
                    else {
                     break;
                    }

                }
            } catch (Exception e) {
            }

            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("DB error: " + e);
        }
        double shortestDistance;

        double minDist = 99999999;
        int index=0;

        for(int i = 0; i < DBList.size(); i++)
        {
            double[] dx = new double[8];
            double sum = 0;
            double distance;
            for(int j = 0; j < 8; j++)
            {
                dx[j] = (double) DBList.get(i).get(j) - grade[j];
                sum += dx[j]*dx[j];
            }

            distance = Double.parseDouble(new DecimalFormat("##.##").format(Math.sqrt(sum)));
            if (distance < minDist)
            {
                minDist = distance;
                index = i+1;
            }
        }
        ArrayList<String> returnlist = new ArrayList<String>();

        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, URL FROM memelib WHERE id ='"+index+"'");
            try{
                if (rs.next()) {
                    url = rs.getString(2);
                }
            }
            catch(Exception e){System.out.println(e);}
            returnlist.add(0,url);
            returnlist.add(1,(1-minDist)*100+"%");
        }
        catch(Exception e){System.out.print(e);}
        return returnlist;
    }
}

