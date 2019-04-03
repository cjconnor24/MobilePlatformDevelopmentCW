/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;

/**
 * Represents Earthquake Entity
 */
public class Earthquake implements Serializable, ClusterItem  {

    // PRIVATE PROPERTIES
    private String id;
    private Location location;
    private Date date;
    private int depth;
    private double magnitude;
    private String category;
    private String link;


    // BLANK CONSTRUCTOR
    public Earthquake() {
        this.location = new Location();
        this.date = new Date();
        this.depth = -1;
        this.magnitude = 99d;
        this.category = "";
        this.link = "";
    }

    /**
     * Retrieve ID
     * @return ID
     */
    public String getId() {
        return extractID();
    }

    /**
     * Set Earthquake ID
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieve the location object
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set location object
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get earthquake date
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set Earthquake data
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get depth of Earthquake
     * @return
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Set earthquake depth
     * @param depth
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Get Earthquake Magnitude
     * @return
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Set earthquake Magnitude
     * @param magnitude
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Set Earthquake Category
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Retrieve earthquake Link
     * @return
     */
    public String getLink() {
        return link;
    }

    /**
     * Set earthquake Link
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Get string representation of Earthqyake
     * @return
     */
    @Override
    public String toString() {
        return "Earthquake{" +
                "location=" + location +
                ", date=" + date +
                ", depth=" + depth +
                ", magnitude=" + magnitude +
                ", category='" + category + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    /**
     * Extract the BGS id based on the link - looks like the datetime stamp
     * @return
     */
    private String extractID(){

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(getLink());
        String id = null;
        if(matcher.find()){

            id = matcher.group(0);
        }

        return id;

    }

    // BELOW METHODS ARE FROM THE CLUSERITEM Class

    /**
     * Get LalLong position for Google Maps
     * @return
     */
    @Override
    public LatLng getPosition() {
        return new LatLng(getLocation().getLat(), getLocation().getLon());
    }

    /**
     * Get the location name for google maps title
     * @return
     */
    @Override
    public String getTitle() {
        return getLocation().getName();
    }

    /**
     * Get snippet window content
     * @return
     */
    @Override
    public String getSnippet() {
        return PrettyDate.getTimeAgo(getDate());
    }

}
