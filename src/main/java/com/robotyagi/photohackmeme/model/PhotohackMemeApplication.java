package com.robotyagi.photohackmeme.model;


import com.robotyagi.photohackmeme.service.PicProcessor;
import com.robotyagi.photohackmeme.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration

public class PhotohackMemeApplication {

	public static void main(String[] args) { SpringApplication.run(PhotohackMemeApplication.class, args);
	PicProcessor ppc = new PicProcessor();
	double[] grade = ppc.getArray(ppc.getEmotions("https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Donald_Trump_official_portrait.jpg/800px-Donald_Trump_official_portrait.jpg"));
	System.out.println(Arrays.toString(grade));
	}

	public class finder {
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

}
