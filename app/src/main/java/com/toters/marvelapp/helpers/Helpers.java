package com.toters.marvelapp.helpers;

import android.content.Context;
import android.util.Log;

import com.toters.marvelapp.R;
import com.toters.marvelapp.models.Characters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

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

    public static void parseCharacters(String response) throws JSONException{

        JSONObject object = new JSONObject(response);
        JSONObject dataObject = object.getJSONObject("data");
        JSONArray resultArray = dataObject.getJSONArray("results");
        Constant.charactersList.clear();

        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject characterObject = resultArray.getJSONObject(i);
            Characters characters = new Characters();
            characters.setId(characterObject.getInt("id"));
            characters.setTitle(characterObject.getString("name"));
            characters.setDescription(characterObject.getString("description"));
            characters.setYear(characterObject.getString("modified").substring(0, 4));

            JSONObject thumbObject = characterObject.getJSONObject("thumbnail");
            String picPath = thumbObject.getString("path") + "." + thumbObject.getString("extension");
            characters.setPicPath(picPath);

            Constant.charactersList.add(characters);
        }
        Log.d(TAG, "parseCharacters: list "+Constant.charactersList);
    }
}
