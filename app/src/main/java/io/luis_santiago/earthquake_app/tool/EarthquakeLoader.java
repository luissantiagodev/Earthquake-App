package io.luis_santiago.earthquake_app.tool;

import android.content.AsyncTaskLoader;
import android.content.Context;
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

import static io.luis_santiago.earthquake_app.EarthquakeActivity.LOG_TAG;
import static io.luis_santiago.earthquake_app.EarthquakeActivity.URL_QUERY;
import static io.luis_santiago.earthquake_app.EarthquakeActivity.carl;

/**
 * Created by legendarywicho on 4/19/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private String murl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        murl = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if(murl== null){
            return null;
        }

        return QueryUtils.fetchDataEarthquake();
    }
}
