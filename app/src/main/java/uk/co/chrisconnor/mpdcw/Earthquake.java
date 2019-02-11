package uk.co.chrisconnor.mpdcw;

public class Earthquake {

    // GENERAL DATA
    private Location location;
    private String dateTime;

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
}
