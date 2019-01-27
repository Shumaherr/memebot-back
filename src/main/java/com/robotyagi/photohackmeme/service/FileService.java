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

@Component
public class FileService {

    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=photohackdiag;" +
                    "AccountKey=dIAgBnoFcBIjiXt2tf/kRyMHIKm2zaPlRAH5dX867wbmdpNbVRyVWFoH52MVW3HxUZtGTWcvb7DxaeszNdSk/A==";
    public static void uploadFile(String file_name, String file_id, Bot bot, int size) throws IOException, URISyntaxException, StorageException, InvalidKeyException {

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



        java.io.FileOutputStream fos = new FileOutputStream("C:\\Users\\Aodintsov\\Desktop\\" + file_name);
        System.out.println("Start upload");
        ReadableByteChannel rbc = Channels.newChannel(downoload.openStream());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);



        CloudFile cloudFile = rootDir.getFileReference(file_name);
        //cloudFile.uploadFromFile(full_path);
        cloudFile.upload(downoload.openStream(), size * 1024 * 1024);
        fos.close();
        rbc.close();
        System.out.println("Uploaded!");
    }



}
