package io.luis_santiago.earthquake_app;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
import io.luis_santiago.earthquake_app.tool.EarthquakeLoader;
import io.luis_santiago.earthquake_app.tool.Keys;

import static android.content.Intent.ACTION_VIEW;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

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

        getSupportLoaderManager().initLoader(0,null,this).forceLoad();

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

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {

    }


}