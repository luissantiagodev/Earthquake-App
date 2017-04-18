package io.luis_santiago.earthquake_app.tool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.luis_santiago.earthquake_app.EarthquakeActivity;
import io.luis_santiago.earthquake_app.R;

import android.graphics.drawable.GradientDrawable;

import static android.os.Build.VERSION_CODES.M;
import static io.luis_santiago.earthquake_app.R.id.city;
import static io.luis_santiago.earthquake_app.R.id.date;

/**
 * Created by legendarywicho on 4/9/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private String part1;
    private String part2;
    private GradientDrawable gradient;

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquake) {
        super(context, 0, earthquake);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.data_activity, parent, false);
        }

        Earthquake earthquake = getItem(position);

        // Getting the magnitude position
        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitud);
        Double mag = earthquake.getMagnitude_earthquake();
        String mag2 = formatMagnitude(mag);

        magnitude.setText(mag2);

        // Finding the city position
        //TODO: Split the city String into two
        String citySplit = earthquake.getLocation_earthqueake();


        if (citySplit.contains("of")) {
            String[] citySplitIntoTwo = citySplit.split("of");
            part1 = citySplitIntoTwo[0]; // The north way of
            part2 = citySplitIntoTwo[1]; // San Francisco, CA

        } else if (citySplit.contains("Near the")) {
            String[] citySplitIntoTwo = citySplit.split("Near the");
            part1 = citySplitIntoTwo[0]; // The north way of
            part2 = citySplitIntoTwo[1]; // San Francisco, CA

        }

        TextView city = (TextView) listItemView.findViewById(R.id.city);
        city.setText(part1);

        TextView location_city = (TextView) listItemView.findViewById(R.id.city_location);
        location_city.setText(part2);

        Log.e("Mensaje the City", part1 + "Este es el segundo String " + part2);
        // Date position
        // TODO: Create a Date object and put the correct format
        Date dateObject = new Date(earthquake.getTime_of_earthquake());
        String formatDate = formatDate(dateObject);

        // Date time with the correct format
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(formatDate);

        // Time position
        TextView time = (TextView) listItemView.findViewById(R.id.time);
        String timeFormat = formatTime(dateObject);
        time.setText(timeFormat);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        gradient = (GradientDrawable) magnitude.getBackground();

        // Setting the correct color
        double magnitudeColor = (earthquake.getMagnitude_earthquake());
        getMagnitudeColor(magnitudeColor);

        return listItemView;
    }


    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        String carl = dateFormat.format(dateObject);
        return carl;
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double mag) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String output = decimalFormat.format(mag);
        return output;
    }

    private void getMagnitudeColor(double d) {
        int i = (int) Math.round(d);
        switch (i) {
            case 0:
            case 1: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 2: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 3: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 4: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 5: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 6: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }

            case 7: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 8: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }
            case 9: {
                int color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                gradient.setColor(color);
                Log.e("Numero redondeado", d + "numero despues" + i);
                break;
            }

        }
    }
}
