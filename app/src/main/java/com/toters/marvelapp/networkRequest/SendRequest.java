package com.toters.marvelapp.networkRequest;

import android.os.AsyncTask;
import android.util.Log;


import com.toters.marvelapp.helpers.Constant;
import com.toters.marvelapp.helpers.Helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendRequest extends AsyncTask<String, String, String> {

    private final String TAG = "SendRequest";
    private final OnRequestComplete listener;
    HttpURLConnection conn;
    private String prefixUrl, parameters;

    public interface OnRequestComplete {
        void onRequestComplete(int status, String response);
    }

    public SendRequest(OnRequestComplete listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            Log.d(TAG, "doInBackground: start");
            prefixUrl = params[0];// characters or characters/1

            parameters = Helpers.getParameters();

            URL url = new URL(Constant.URL + prefixUrl + "?" + parameters);
            Log.d(TAG, "doInBackground: url " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(3000);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.connect();

            int response_code = conn.getResponseCode();
            Log.d(TAG, "doInBackground: response code " + response_code);

            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return (result.toString());
            } else {
                return "connection";
            }

        } catch (Exception e) {
            Log.d(TAG, "doInBackground: error " + e.getMessage());
            return "connection";
        } finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute: result " + result);

        if (result.equalsIgnoreCase("connection")) {
            listener.onRequestComplete(0, "Couldn't connect to server, please try again later");
            return;
        }

        listener.onRequestComplete(1, result);
    }
}
