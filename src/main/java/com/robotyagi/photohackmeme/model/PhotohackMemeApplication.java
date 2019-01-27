package com.robotyagi.photohackmeme.model;


import com.robotyagi.photohackmeme.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration

public class PhotohackMemeApplication {

	public static void main(String[] args) { SpringApplication.run(PhotohackMemeApplication.class, args);}

}
