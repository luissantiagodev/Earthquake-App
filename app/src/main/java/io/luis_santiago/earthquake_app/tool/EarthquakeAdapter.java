package io.luis_santiago.earthquake_app.tool;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import io.luis_santiago.earthquake_app.R;

/**
 * Created by legendarywicho on 4/9/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList <Earthquake> earthquake){

        super(context, 0, earthquake);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.data_activity,parent,false);
        }

        Earthquake earthquake = getItem(position);

        // Getting the magnitude position
        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitud);

        magnitude.setText(Double.toString(earthquake.getMagnitude_earthquake()));

        // Finding the city position

        TextView city = (TextView) listItemView.findViewById(R.id.city);

        city.setText(earthquake.getLocation_earthqueake());

        // Date position

        TextView date = (TextView) listItemView.findViewById(R.id.date);

        date.setText(earthquake.getTime_of_earthquake());


        return listItemView;
    }
}
