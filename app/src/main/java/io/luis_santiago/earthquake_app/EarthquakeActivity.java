package io.luis_santiago.earthquake_app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import io.luis_santiago.earthquake_app.tool.Earthquake;
import io.luis_santiago.earthquake_app.tool.EarthquakeAdapter;
import io.luis_santiago.earthquake_app.tool.Keys;

import static android.content.Intent.ACTION_VIEW;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final String URL_QUERY = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    ListView earthquakeListView;
    ArrayList <Earthquake> carl = new ArrayList<>();
    Intent intent;
    EarthquakeAdapter earthquakeAdapterm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);



        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        QueryUtils queryUtils = new QueryUtils();
        queryUtils.execute();



        //TODO: set up the correct url
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake earthquake = earthquakeAdapterm.getItem(i);
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(earthquake.getUrl()));
                startActivity(intent);
            }
        });
    }


    private class QueryUtils extends AsyncTask <String,Void,ArrayList<Earthquake>>{
        @Override
        protected ArrayList <Earthquake> doInBackground(String... urls) {
            URL url = createUrl(URL_QUERY);
            String jsonResponse = "";
            try{
                jsonResponse = makeHttpRequest(url);
            }
            catch(IOException e){
                Log.e(LOG_TAG, "There was an error making the request");
            }

            carl = extractEarthquakeData(jsonResponse);

            return carl;
        }

        @Override
        protected void onPostExecute(ArrayList <Earthquake> arrayList) {

            if(carl== null){
                Log.e(LOG_TAG,"There was something wrong, list is null");
            }
            earthquakeAdapterm = new EarthquakeAdapter(EarthquakeActivity.this, carl);
            earthquakeListView.setAdapter(earthquakeAdapterm);
        }

        private QueryUtils(){ // Private constructor so we cant create an instance
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
    }

}