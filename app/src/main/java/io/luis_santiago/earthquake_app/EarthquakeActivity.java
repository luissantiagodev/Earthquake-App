package io.luis_santiago.earthquake_app;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import io.luis_santiago.earthquake_app.tool.Earthquake;
import io.luis_santiago.earthquake_app.tool.EarthquakeAdapter;
import io.luis_santiago.earthquake_app.tool.EarthquakeLoader;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class EarthquakeActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final int EARTHQUAKE_LOADER = 1;
    public static final String URL_QUERY = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    ListView earthquakeListView;
    TextView noInternetConection;
    ProgressBar progressBar;
    public static ArrayList<Earthquake> carl = new ArrayList<>();
    Intent intent;
    EarthquakeAdapter earthquakeAdapterm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        init();

        //TODO: verify if there's internet connection

            if(thereIsInternet()){
                progressBar.setVisibility(View.VISIBLE);
            }
            else if(!(thereIsInternet())){
                progressBar.setVisibility(View.GONE);
                noInternetConection.setText("There is no internet connection");
            }
        // Find a reference to the {@link ListView} in the layout
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER, null, this);

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
    public android.content.Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this,URL_QUERY);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
            if(earthquakes!=null && !(earthquakes.isEmpty())) {
                earthquakeListView.setVisibility(View.VISIBLE);
                Log.e(LOG_TAG,"The list was able to get on the content"+ earthquakes);
                earthquakeAdapterm = new EarthquakeAdapter(this, earthquakes);
                earthquakeListView.setAdapter(earthquakeAdapterm);
                progressBar.setVisibility(View.GONE);
            }
            else if (earthquakes.isEmpty()) {
                noInternetConection.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.e(LOG_TAG,"There is no internet conection");
                Log.e(LOG_TAG,"el array esta vacio");
            }

    }
    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<Earthquake>> loader) {
        earthquakeAdapterm.clear();
    }

    public void init(){
        earthquakeListView = (ListView) findViewById(R.id.list);
        noInternetConection = (TextView)findViewById(R.id.no_internet_conection);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        earthquakeListView.setVisibility(View.GONE);
        noInternetConection.setVisibility(View.GONE);
    }

    public boolean thereIsInternet(){

        ConnectivityManager connectivityManager = (ConnectivityManager)EarthquakeActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activework  = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activework!=null && activework.isConnected();
        return isConnected;
    }
}