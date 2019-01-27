package com.robotyagi.photohackmeme;

import com.robotyagi.photohackmeme.service.PicProcessor;
import com.robotyagi.photohackmeme.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class PhotohackMemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotohackMemeApplication.class, args);
		PicProcessor pp = new PicProcessor();
		String emo = pp.getEmotions("http://pluspng.com/img-png/png-surprised-300-300-in-surprised-girl-300.png");
		double[] emotions = pp.getArray(emo);
		SearchService sser = new SearchService();
		String temp = sser.getUrl(emotions);
		String result = pp.getPicAPI("http://pluspng.com/img-png/png-surprised-300-300-in-surprised-girl-300.png", temp);
		System.out.println(result);
	}

}

