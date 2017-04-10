package io.luis_santiago.earthquake_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import io.luis_santiago.earthquake_app.tool.Earthquake;
import io.luis_santiago.earthquake_app.tool.EarthquakeAdapter;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ArrayList<Earthquake> earth_info = QueryUtils.extractEarthquakes();

        EarthquakeAdapter earthquakeSetupAdapter_m = new EarthquakeAdapter(this, earth_info);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        earthquakeListView.setAdapter(earthquakeSetupAdapter_m);
    }
}