package com.robotyagi.photohackmeme.service;

import com.robotyagi.photohackmeme.model.Memes;
import com.robotyagi.photohackmeme.repository.MemesRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configurable
@Service
public class SearchService {

    @Autowired
    MemesRepository memerepo;

    public String getNearestMeme(Memes memes) {
        List<Memes> allMemes = new ArrayList();
        allMemes = memerepo.findAll();

        int index = SearchSmallestDistanceObject(allMemes, memes);
        return allMemes.get(index).getUrl();
    }

    private int SearchSmallestDistanceObject(List<Memes> ObjectList, Memes object){
        int i = 0;
        double distance;
        int position=0;
        distance = Math.sqrt(Math.pow ((object.getAnger()-ObjectList.get(0).getAnger()), 2)+
                Math.pow ((object.getContempt()-ObjectList.get(0).getContempt()), 2)+
                Math.pow ((object.getDisgust()-ObjectList.get(0).getDisgust()), 2)+
                Math.pow ((object.getFear()-ObjectList.get(0).getFear()), 2)+
                Math.pow ((object.getHappiness()-ObjectList.get(0).getHappiness()), 2)+
                Math.pow ((object.getNeutral()-ObjectList.get(0).getNeutral()), 2)+
                Math.pow ((object.getSadness()-ObjectList.get(0).getSadness()), 2)+
                Math.pow ((object.getSurprise()-ObjectList.get(0).getSurprise()), 2));
        for (Memes o:
                ObjectList) {
            double tmp = Math.sqrt(Math.pow ((object.getAnger()-o.getAnger()), 2)+
                    Math.pow ((object.getContempt()-o.getContempt()), 2)+
                    Math.pow ((object.getDisgust()-o.getDisgust()), 2)+
                    Math.pow ((object.getFear()-o.getFear()), 2)+
                    Math.pow ((object.getHappiness()-o.getHappiness()), 2)+
                    Math.pow ((object.getNeutral()-o.getNeutral()), 2)+
                    Math.pow ((object.getSadness()-o.getSadness()), 2)+
                    Math.pow ((object.getSurprise()-o.getSurprise()), 2));
            if (tmp < distance){
                position = i;
            }
            i++;
        }
        return position;
    }
}

