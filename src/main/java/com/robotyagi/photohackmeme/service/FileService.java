package com.robotyagi.photohackmeme.service;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.file.*;
import com.robotyagi.photohackmeme.model.Bot;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class FileService {

    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=photohackdiag;" +
                    "AccountKey=dIAgBnoFcBIjiXt2tf/kRyMHIKm2zaPlRAH5dX867wbmdpNbVRyVWFoH52MVW3HxUZtGTWcvb7DxaeszNdSk/A==";
    public static ArrayList<String> uploadFile(String file_name, String file_id, Bot bot, int size) throws IOException, URISyntaxException, StorageException, InvalidKeyException {

        CloudStorageAccount   storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudFileClient fileClient = storageAccount.createCloudFileClient();

        CloudFileShare share = fileClient.getShareReference("uploads");
        CloudFileDirectory rootDir = share.getRootDirectoryReference();

        URL url = new URL("https://api.telegram.org/bot"+bot.getBotToken()+"/getFile?file_id="+file_id);
        BufferedReader in = new BufferedReader(new InputStreamReader( url.openStream()));
        String res = in.readLine();
        JSONObject jresult = new JSONObject(res);
        JSONObject path = jresult.getJSONObject("result");
        String file_path = path.getString("file_path");
        String full_path = "https://api.telegram.org/file/bot" + bot.getBotToken() + "/" + file_path;
        URL downoload = new URL(full_path);

        System.out.println("Start upload");
        CloudFile cloudFile = rootDir.getFileReference(file_name);
        //cloudFile.uploadFromFile(full_path);
        cloudFile.upload(downoload.openStream(), size * 1024 * 1024);
        System.out.println("Uploaded!");
        String fileOutUrl = "https://photohackdiag.file.core.windows.net/uploads/" + file_name + "?sv=2018-03-28&ss=bfqt&srt=sco&sp=rwdlacup&se=2019-03-27T16:50:41Z&st=2019-01-27T08:50:41Z&spr=https&sig=6vXfX79084zfiRZL97XFlcF5XhXFX7ytsjMdyWUsMo4%3D";
        PicProcessor picProcessor = new PicProcessor();
        ArrayList<String> meme = picProcessor.processImage(fileOutUrl);
        return meme;
    }



}
