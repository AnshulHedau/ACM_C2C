package com.sada.edubuddy;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shantanu on 3/12/2018.
 */

class BotMessageLoader extends AsyncTaskLoader<HashMap<String,String>> {

    private static final String TAG = "BotMessageLoader";
    private String queryUrl;

    public BotMessageLoader(Context context, String topHeadlinesUrl) {
        super(context);
        queryUrl = topHeadlinesUrl;
    }

    @Override
    protected void onStartLoading() {
//        Log.i(TAG, "onStartLoading: forceLoad : CALLED");
        forceLoad();
    }

    @Override
    public HashMap<String,String> loadInBackground() {
//        Log.i(TAG, "loadInBackground: CALLED");
        if (queryUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        HashMap<String,String> message = null;
        try {
            message = QueryUtils.fetchNewsData(queryUrl);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "loadInBackground: PROBLEM FETCHING DATA : ", e);
        }
        return message;
    }
}
