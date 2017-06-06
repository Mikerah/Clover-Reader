package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import android.util.Log;

import net.dongliu.requests.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mikerah on 5/28/2017.
 */

public class ChanHelper {

    public static Object getJSONFromUrl(String url, boolean isJsonArray) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while( (line = reader.readLine()) != null){
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);
            }

            if(!isJsonArray) {
                JSONObject jsonObject = new JSONObject(buffer.toString());
                return  jsonObject;
            } else {
                JSONArray jsonArray = new JSONArray(buffer.toString());
                return jsonArray;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
