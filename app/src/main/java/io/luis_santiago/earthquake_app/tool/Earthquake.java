package io.luis_santiago.earthquake_app.tool;

/**
 * Created by legendarywicho on 4/9/17.
 */

public class Earthquake {

    private double magnitude_earthquake;
    private String location_earthqueake;
    private long time_of_earthquake;
    private String url;

    public Earthquake(double magnitude, String location, long date, String urlm){
        this.magnitude_earthquake = magnitude;
        this.location_earthqueake = location;
        this.time_of_earthquake = date;
        this.url = urlm;
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

    public String getUrl(){
        return url;
    }

}