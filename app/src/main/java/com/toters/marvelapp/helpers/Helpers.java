package com.toters.marvelapp.helpers;

import android.content.Context;
import android.util.Log;

import com.toters.marvelapp.R;
import com.toters.marvelapp.models.Characters;
import com.toters.marvelapp.models.Comics;
import com.toters.marvelapp.models.Content;
import com.toters.marvelapp.models.Events;
import com.toters.marvelapp.models.Series;
import com.toters.marvelapp.models.Stories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Parse all json data
 */

public class Helpers {

    private static final String TAG = "Helpers";

    public static String getParameters() {
        long ts = Calendar.getInstance().getTimeInMillis();
        return String.format("ts=%s&apikey=%s&hash=%s",
                ts,
                Constant.API_PUBLIC_KEY,
                Helpers.getHash(ts));
    }

    public static String getHash(long ts) {
        return Helpers.getHash(String.format("%s%s%s", ts, Constant.API_PRIVATE_KEY, Constant.API_PUBLIC_KEY));
    }

    public static String getHash(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getBoolean(R.bool.isLandscape);
    }

    public static void parseCharacters(String response) throws JSONException {

        JSONObject object = new JSONObject(response);
        JSONObject dataObject = object.getJSONObject("data");
        JSONArray resultArray = dataObject.getJSONArray("results");
        Constant.charactersList.clear();

        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject characterObject = resultArray.getJSONObject(i);
            Characters characters = new Characters();
            characters.setId(characterObject.getInt("id"));
            characters.setTitle(characterObject.getString("name"));
            characters.setDescription(characterObject.getString("description").length() > 0 ? characterObject.getString("description") : "No description found");
            characters.setYear(characterObject.getString("modified").substring(0, 4));
            characters.setRate(String.valueOf((float) (Math.random() * 5)).substring(0, 3));
            JSONObject thumbObject = characterObject.getJSONObject("thumbnail");
            String picPath = "";
            if (thumbObject.length() > 0) {
                picPath = String.format("%s.%s", thumbObject.getString("path"), thumbObject.getString("extension"));
            }
            characters.setPicPath(picPath);

            Constant.charactersList.add(characters);
        }
        Log.d(TAG, "parseCharacters: list " + Constant.charactersList);
    }

    public static Content getComics(String response) {
        List<Object> objectList = new ArrayList<>();
        Content content = new Content();
        try {
            JSONObject object = new JSONObject(response);
            JSONObject dataObject = object.getJSONObject("data");
            JSONArray resultArray = dataObject.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject comicObject = resultArray.getJSONObject(i);
                JSONObject pictureObject = comicObject.getJSONObject("thumbnail");
                String picPath = "";
                if (pictureObject.has("path") && pictureObject.has("extension")) {
                    picPath = String.format("%s.%s", pictureObject.getString("path"), pictureObject.getString("extension"));
                }
                Comics comics = new Comics(
                        comicObject.getInt("id"),
                        comicObject.getString("title"),
                        picPath);
                if (comicObject.getString("description") != null && !comicObject.getString("description").equalsIgnoreCase("null")) {
                    comics.setDescription(comicObject.getString("description"));
                }
                objectList.add(comics);
            }
            content.setTitle("Comics");
            content.setObjectList(objectList);
            return content;
        } catch (JSONException e) {
            Log.d(TAG, "getComics: error " + e.getMessage());
            return content;
        }
    }

    public static Content getEvents(String response) {
        List<Object> objectList = new ArrayList<>();
        Content content = new Content();
        try {
            JSONObject object = new JSONObject(response);
            JSONObject dataObject = object.getJSONObject("data");
            JSONArray resultArray = dataObject.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject comicObject = resultArray.getJSONObject(i);
                JSONObject pictureObject = comicObject.getJSONObject("thumbnail");
                String picPath = "";
                if (pictureObject.has("path") && pictureObject.has("extension")) {
                    picPath = String.format("%s.%s", pictureObject.getString("path"), pictureObject.getString("extension"));
                }
                Events events = new Events(
                        comicObject.getInt("id"),
                        comicObject.getString("title"),
                        comicObject.getString("description").length() > 0 ? comicObject.getString("description") : "No Description found",
                        picPath);

                objectList.add(events);
            }
            content.setTitle("Events");
            content.setObjectList(objectList);
            return content;

        } catch (JSONException e) {
            Log.d(TAG, "getEvents: error " + e.getMessage());
            return content;
        }
    }

    public static Content getStories(String response) {
        List<Object> objectList = new ArrayList<>();
        Content content = new Content();
        try {
            JSONObject object = new JSONObject(response);
            JSONObject dataObject = object.getJSONObject("data");
            JSONArray resultArray = dataObject.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject comicObject = resultArray.getJSONObject(i);
                String picPath = "";

                Stories stories = new Stories(
                        comicObject.getInt("id"),
                        comicObject.getString("title"),
                        comicObject.getString("description").length() > 0 ? comicObject.getString("description") : "No Description found",
                        comicObject.getString("type"),
                        picPath);

                objectList.add(stories);
            }
            content.setTitle("Stories");
            content.setObjectList(objectList);
            return content;
        } catch (JSONException e) {
            Log.d(TAG, "getStories: error " + e.getMessage());
            return content;
        }
    }

    public static Content getSeries(String response) {
        List<Object> objectList = new ArrayList<>();
        Content content = new Content();
        try {
            JSONObject object = new JSONObject(response);
            JSONObject dataObject = object.getJSONObject("data");
            JSONArray resultArray = dataObject.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject comicObject = resultArray.getJSONObject(i);
                JSONObject pictureObject = comicObject.getJSONObject("thumbnail");
                String picPath = "";
                if (pictureObject.has("path") && pictureObject.has("extension")) {
                    picPath = String.format("%s.%s", pictureObject.getString("path"), pictureObject.getString("extension"));
                }
                Series series = new Series(
                        comicObject.getInt("id"),
                        comicObject.getString("title"),
                        comicObject.getString("description").length() > 0 ? comicObject.getString("description") : "No Description found",
                        picPath);

                objectList.add(series);
            }
            content.setTitle("Series");
            content.setObjectList(objectList);
            return content;
        } catch (JSONException e) {
            Log.d(TAG, "getSeries: error " + e.getMessage());
            return content;
        }
    }

    public static List<Content> filterContentList(List<Content> contentList) {
        List<Content> contentList1 = new ArrayList<>();
        for (Content content : contentList) {
            if (content.getObjectList().size() > 0) {
                contentList1.add(content);
            }
        }
        return contentList1;
    }
}
