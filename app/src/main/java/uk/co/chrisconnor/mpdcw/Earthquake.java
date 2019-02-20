package uk.co.chrisconnor.mpdcw;

import java.util.Date;

public class Earthquake {

    // GENERAL DATA
    private Location location;
    private String dateTime;

    // TODO: THIS NEEDS FIXED
    // Wed, 30 Jan 2019 11:53:15
    // DD, DD MM YYYY HH:MM:SS
    private Date date;

    // STAT DATA
    private int depth;
    private double magnitude;

    // META
    private String category;
    private String link;


//    X Location
//    X DateTime
//    X Position
//    X Depth
//    X Magnitude
//    Category
    // Link


    // BLANK CONSTRUCTOR
    public Earthquake() {
        this.location = new Location();
        this.dateTime = "";
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
                ", dateTime='" + dateTime + '\'' +
                ", depth=" + depth +
                ", magnitude=" + magnitude +
                ", category='" + category + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    //
}
