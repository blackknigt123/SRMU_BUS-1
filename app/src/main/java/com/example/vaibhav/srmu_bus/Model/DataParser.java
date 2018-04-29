package com.example.vaibhav.srmu_bus.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataParser {

    private HashMap<String,String>getDuration(JSONArray googleDurationJson)
    {
        HashMap<String,String> gooleDurationMap=new HashMap<>();
        String duration="";
        String distance="";
        Log.d("json response",googleDurationJson.toString());

        try {
            duration=googleDurationJson.getJSONObject(0).
                    getJSONObject("duration").getString("text");
            distance=googleDurationJson.getJSONObject(0).
                    getJSONObject("distance").getString("text");

            gooleDurationMap.put("duration",duration);
            gooleDurationMap.put("distance",distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gooleDurationMap;
    }

    public String[] parseDirections(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0)
                    .getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPaths(jsonArray);
    }

    public String[] getPaths(JSONArray googleStepsJson) {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];

        for (int i = 0; i < count; i++) {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return polylines;
    }

    public String getPath(JSONObject googlePathJson) {
        String polyline = "";
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polyline;
    }

    public HashMap<String, String> parseDuration(String jsonData) {

        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).
                    getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();

            }

            return getDuration(jsonArray);
    }
}
