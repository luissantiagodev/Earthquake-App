package io.luis_santiago.earthquake_app.tool;

/**
 * Created by legendarywicho on 4/9/17.
 */

public class Earthquake {

    private double magnitude_earthquake;
    private String location_earthqueake;
    private long time_of_earthquake;

    public Earthquake(double magnitude, String location, long date){
        this.magnitude_earthquake = magnitude;
        this.location_earthqueake = location;
        this.time_of_earthquake = date;
    }

    public double getMagnitude_earthquake(){
        return magnitude_earthquake;
    }

    public String getLocation_earthqueake(){
        return location_earthqueake;
    }

    public long getTime_of_earthquake(){
        return time_of_earthquake;
    }


}