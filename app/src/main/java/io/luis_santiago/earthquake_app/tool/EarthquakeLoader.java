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

/**
 * Created by legendarywicho on 4/19/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private String murl;

    public EarthquakeLoader(Context context, String url){
        super(context);
        this.murl=url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        if(murl==null){
            return null;
        }
      ArrayList <Earthquake> finalResutl =  fecthDataEarthquake();
        return null;
    }



    public ArrayList<Earthquake> fecthDataEarthquake () {
        ArrayList <Earthquake> carl = new ArrayList<>();
        if(murl==null){
            return null;
        }
        URL curl = createUrl(URL_QUERY);
        String jsonResponse = "";
        try{
            jsonResponse = makeHttpRequest(curl);
        }
        catch(IOException e){
            Log.e(LOG_TAG, "There was an error making the request");
        }
        // global arraylist
        carl = extractEarthquakeData(jsonResponse);

        return carl;
    }


    public String makeHttpRequest(URL url) throws IOException {
        String jsonresponse = "";
        if (url==null) {
            return jsonresponse;
        }
        HttpURLConnection urlConnection= null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonresponse = readStreamFrom(inputStream);
            } else {
                Log.e(LOG_TAG, "Error closing input stream" + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonresponse;
    }

    private String readStreamFrom(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public ArrayList extractEarthquakeData (String earthquakeJSON){

            ArrayList <Earthquake> carl = new ArrayList<>();
        try {
            JSONObject jsonRoot = new JSONObject(earthquakeJSON);
            JSONArray feature = jsonRoot.getJSONArray("features");

            for (int i = 0; i<feature.length(); i++){
                JSONObject earthquake = feature.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");

                // Setting up the correct variables
                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Log.e(LOG_TAG,place);
                Earthquake earthquakeObject = new Earthquake(magnitude,place,time,url);
                carl.add(earthquakeObject);
            }
        }
        catch (JSONException e){
            Log.e(LOG_TAG,"There was something wrong with the URL.");
        }
        return carl;
    }

    public URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG,"There was a error forming the URL string.");
        }
        return url;
    }
}
