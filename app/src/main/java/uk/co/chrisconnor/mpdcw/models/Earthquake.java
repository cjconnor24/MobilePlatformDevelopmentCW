package uk.co.chrisconnor.mpdcw.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;

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

    public String getId() {
        return extractID();
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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

    private String extractID(){

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(getLink());
        String id = null;
        if(matcher.find()){

            id = matcher.group(0);
        }

        return id;

    }

    @Override
    public LatLng getPosition() {
        return new LatLng(getLocation().getLat(), getLocation().getLon());
    }

    @Override
    public String getTitle() {
        return getLocation().getName();
    }

    @Override
    public String getSnippet() {
        return PrettyDate.getTimeAgo(getDate());
    }

}
