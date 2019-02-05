package com.robotyagi.photohackmeme.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MessageService {


    public URL getPhotoFromMessage(Message message) throws MalformedURLException {
        URL url = new URL("");
        return url ;
    }
}
