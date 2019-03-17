package com.robotyagi.photohackmeme.controller;

import java.io.*;
import java.util.Vector;

import com.robotyagi.photohackmeme.service.MessageService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller

public class FileUploadController {
    @Autowired
    MessageService messageService;
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public ResponseEntity<?> newFile(@RequestBody String bodyString){
        JSONObject body = new JSONObject(bodyString);
        String fileUrl = new String();
        String text = new String();
            try {
                fileUrl = body.getString("url");
                text = body.getString("text");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        Vector<String> result = messageService.getMessageResponse(fileUrl);
            return ResponseEntity.ok(result);
    }
}
