package com.sada.edubuddy;

import android.text.TextUtils;
import android.util.Log;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shaan on 26-01-18.
 */

public final class QueryUtils {
    private static final String TAG = "QueryUtils";

    private QueryUtils() {
    }

    public static HashMap<String,String> fetchNewsData(String requestUrl) throws IOException {
//        Log.i(TAG, "fetchNewsData: CALLED");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.i(TAG, "fetchNewsData: JSON RESPONSE FETCHED");
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.");
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        HashMap<String,String> outputFromBot = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}s
        return outputFromBot;
    }

    private static URL createUrl(String stringUrl) {
//        Log.i(TAG, "createUrl: CALLED");
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
//        Log.i(TAG, "makeHttpRequest: CALLED");
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the news JSON results.");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static HashMap<String,String> extractFeatureFromJson(String newsJSON) {
//        Log.i(TAG, "extractFeatureFromJson: CALLED");
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        HashMap<String,String> outputFromBot = new HashMap<String,String>();

        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            String intent = baseJsonResponse.getString("intent");
            String data_1 = baseJsonResponse.getString("data_1");
            String data_2 = baseJsonResponse.getString("data_2");

            outputFromBot.put("intent", intent);
            outputFromBot.put("data_1", data_1);
            outputFromBot.put("data_2",data_2);
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        return outputFromBot;
    }
}
