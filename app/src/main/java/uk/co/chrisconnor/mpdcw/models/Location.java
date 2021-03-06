/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

/**
 * Represents Earthquake Location
 */
public class Location implements Serializable {

    private String name;
    private double lat;
    private double lon;

    /**
     * Constructor
     */
    public Location() {
        this.name = "";
        this.lat = 9999d;
        this.lon = 9999d;
    }

    /**
     * Overloaded constructor
     * @param name Name of location
     * @param lat Latitude of location
     * @param lon Longitude of location
     */
    public Location(String name, double lat, double lon) {
        this.setName(name);
        this.setLat(lat);
        this.setLon(lon);
    }

    /**
     * Overloaded constructor to parse lat and long
     * @param name
     * @param latlon
     */
    public Location(String name, String latlon) {
        this.parseLocation(name, latlon);
    }

    // GETTERS AND SETTERS
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    /**
     * Set the name and location from strings
     * @param name
     * @param latlong
     */
    private void parseLocation(String name, String latlong){

        this.setName(name.replace(" ;", "").trim());

        try {
            this.setLat(Double.parseDouble(latlong.split(",")[0].replace(" ;", "").trim()));
            this.setLon(Double.parseDouble(latlong.split(",")[1].replace(" ;", "").trim()));
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

    }
}
