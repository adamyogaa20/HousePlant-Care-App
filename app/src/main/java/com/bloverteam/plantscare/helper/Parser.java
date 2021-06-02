package com.bloverteam.plantscare.helper;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Parser{

    public static int CLASS_NUM = 76;
    private static Parser jsonParser = null;
    private static HashMap<String, HashMap<String, String>> plant_info = null;

    private Parser(){}

    public static Parser getInstance(InputStream is){

        if(jsonParser == null){
            plant_info = new HashMap<>();
            jsonParser = new Parser();
            jsonParser.parseJSON(is);
        }

        return jsonParser;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String loadJSON(InputStream is){
        String json = null;

        try{
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
        }

        return json;
    }

    private void parseJSON(InputStream is){
        String json = loadJSON(is);

        try {
            JSONObject jsonObject = new JSONObject(json);
            HashMap<String, String> child;

            for(int i = 0; i < CLASS_NUM; i++){
                JSONObject parent = jsonObject.getJSONObject(""+i);
                child = new HashMap<>();
                child.put("name", parent.getString("name"));
                child.put("family", parent.getString("family"));
                child.put("genus", parent.getString("genus"));
                child.put("caraMerawat", parent.getString("caraMerawat"));
                plant_info.put(""+i, child);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getItem(int id, String key){
        return plant_info.get(""+id).get(key);
    }
}
