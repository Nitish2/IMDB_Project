package com.example.hp.imdb.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Json_Data {

    public Json_Data() {}
    public JSONObject getJSONFromURL(String url) {
        InputStream inputStream = null;
        String result = "";
        JSONObject jsonObject = null;

        //Here Download JSON data from URL
        try {
            URL mUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            //Here Set method for request
            httpURLConnection.setRequestMethod("GET");
            //Here we use the URL connection for input
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == 200) {
                Log.e("Server connection", "Successful");
                //here if response code = 200 ok
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            }
        } catch (Exception e) {
            Log.e("log_tag", "Error in Http connection " + e.toString());
        }

        // Here We Convert response to string
        try {
            //Buffered reader wraps input stream
            //here input stream contains ISO-8859-15 encoded text
            if(inputStream!=null)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    String enter = "\n";
                    sb.append(enter);
                }
                inputStream.close();
                result = sb.toString();
            }
            else {
                Log.e("Inputstream :", "is null");
            }
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jsonObject;
    }

}
