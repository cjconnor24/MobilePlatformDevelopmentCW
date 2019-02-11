package uk.co.chrisconnor.mpdcw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    private List<Earthquake> earthquakes = new ArrayList<Earthquake>();
    private Earthquake earthquake;
    private String text = "";

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public List<Earthquake> parse(InputStream is) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = parser.getName();

                if (eventType == XmlPullParser.START_TAG) {

                    if (parser.getName().equals("item")) {
                        System.out.println("CREATING NEW EARTHQUAKE");
                        earthquake = new Earthquake();
                    }

                } else if (eventType == XmlPullParser.TEXT) {

                    // GET THE ACTUAL TEXT HERE
                    text = parser.getText();

                } else if (eventType == XmlPullParser.END_TAG) {

                    // REACHED THE END OF THE TAG

                    if (parser.getName().equals("item")) {

                        earthquakes.add(earthquake);
                    } else if (parser.getName().equals("link")){
                        if(earthquake != null){

                        earthquake.setLink(text);
                        }
                    } else if (parser.getName().equals("description")){

                        if(earthquake != null){
                        String[] elements;
                        elements = text.split("([A-Za-z\\/ ]+?):");

                        try {

                            // 0 BLANK
                            // 1 DATE
                            // 2 LOCATION NAME
                            Location loc = new Location();
                            loc.setName(elements[2].replace(" ;","").trim());
                            // 3 LAT LONG
                            loc.setLat(Double.parseDouble(elements[3].split(",")[0].replace(" ;","").trim()));
                            loc.setLon(Double.parseDouble(elements[3].split(",")[1].replace(" ;","").trim()));
                            earthquake.setLocation(loc);

                            // 4 DEPTH
                            earthquake.setDepth(Integer.parseInt(elements[4].replace(" km ;","").trim()));
                            // 5 Magnitude
                            earthquake.setMagnitude(Double.parseDouble(elements[5].trim()));

                        } catch (Exception e){
                            Log.e("x",e.toString());
                        }

                        }
                    } else if (parser.getName().equals("category")){
                        if(earthquake != null){

                            earthquake.setCategory(text);
                        }
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

}
