package uk.co.chrisconnor.mpdcw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseEarthquakes {

    private static final String TAG = "ParseEarthquakes";
    private ArrayList<Earthquake> earthquakes;
    private Earthquake earthquake;
    private String text = "";

    public ParseEarthquakes() {
        this.earthquakes = new ArrayList<>();
    }

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public List<Earthquake> parse(String xml) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = parser.getName();

                if (eventType == XmlPullParser.START_TAG) {

                    if (tagname.equals("item")) {
                        // INITIALISE THE EARTHQUAKE
                        earthquake = new Earthquake();
                    }

                } else if (eventType == XmlPullParser.TEXT) {

                    // GET THE ACTUAL TEXT HERE
                    text = parser.getText();

                } else if (eventType == XmlPullParser.END_TAG) {

                    // REACHED THE END OF THE TAG

                    if (tagname.equals("item")) {

                        earthquakes.add(earthquake);
                    } else if (tagname.equals("link")) {
                        if (earthquake != null) {

                            earthquake.setLink(text);
                        }
                    } else if (tagname.equals("description") && earthquake != null) {

                        // PARSE THE DESCRIPTION STRING TO GET ALL THE TAGS
                        String[] elements;
                        elements = text.split("([A-Za-z\\/ ]+?):");

//                            try {

                        // 0 BLANK
                        // 1 DATE
                        earthquake.setDateTime(elements[1].replace(" ;", "").trim());
                        // TODO: MAKE THIS AN ACTUAL DATETIME OBJECT


                        // 2 LOCATION NAME
                        Location loc = parseLocation(elements[2], elements[3]);
                        earthquake.setLocation(loc);

                        // 4 DEPTH
                        earthquake.setDepth(Integer.parseInt(elements[4].replace(" km ;", "").trim()));
                        // 5 Magnitude
                        earthquake.setMagnitude(Double.parseDouble(elements[5].trim()));

//                            } catch (Exception e) {
//                                Log.e("x", e.toString());
//                            }


                    } else if (parser.getName().equals("category") && earthquake != null) {

                        earthquake.setCategory(text);

                    }

                }

                eventType = parser.nextToken();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return earthquakes;
    }

    /**
     * Create location object from passed strings
     *
     * @param name    Name String
     * @param latlong LatLong String
     * @return
     */
    private Location parseLocation(String name, String latlong) {

        Location loc = new Location();
        loc.setName(name.replace(" ;", "").trim());

        try {

            loc.setLat(Double.parseDouble(latlong.split(",")[0].replace(" ;", "").trim()));
            loc.setLon(Double.parseDouble(latlong.split(",")[1].replace(" ;", "").trim()));

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        return loc;

    }

}
