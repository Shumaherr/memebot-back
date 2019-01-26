package com.robotyagi.photohackmeme;

import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller

public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String newFile(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                try (FileOutputStream stream = new FileOutputStream("path/file name")) {
                    stream.write(bytes);
                }

                return "File saved!";
            } catch (Exception e) {
                return "Couldn't save file!";
            }
        } else {
            return "Empty file!";
        }
    }
}
