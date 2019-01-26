package com.robotyagi.photohackmeme.service;

import org.json.JSONArray;
import org.json.JSONObject;

public class jsonParser {
    public double[] getArray(String jsonin)
    {
        String url = jsonin.substring(1,jsonin.length()-1);
        JSONObject obj = new JSONObject(jsonin).getJSONObject("faceAttributes");

        JSONArray arr = obj.getJSONArray("emotion");

        double[] grade= new double[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            double elem = arr.getDouble(i);
            grade[i] = elem;
        }

        return grade;
    }
}
