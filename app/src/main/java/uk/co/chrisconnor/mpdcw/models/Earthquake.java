package uk.co.chrisconnor.mpdcw.models;

import java.io.Serializable;
import java.util.Date;

public class Earthquake implements Serializable {

    // PRIVATE PROPERTIES
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
}
