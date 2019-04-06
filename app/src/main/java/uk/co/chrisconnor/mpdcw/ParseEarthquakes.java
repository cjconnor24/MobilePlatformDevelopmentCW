/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;
import uk.co.chrisconnor.mpdcw.models.Location;

public class ParseEarthquakes {

    private static final String TAG = "ParseEarthquakes";
    private ArrayList<Earthquake> earthquakes;

    public ParseEarthquakes() {
        this.earthquakes = new ArrayList<>();
    }

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    /**
     * Parse the xml data
     * @param xml XML String of data to be parsed
     * @return boolean
     */
    public boolean parse(String xml) {

        boolean status = true;
        Earthquake currentEarthquake = null;
        String text = "";

        try {

            // SETUP PULL PARSER AND PLACEHOLDERS
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();

            // TODO: TIDYUP THE WAY ITS CHECKING FOR NULLS
            // TODO: SET A BOOLEAN FOR WHEN WE HIT THE MAIN ITEMS

            // LOOP UNTIL END OF DOC
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = parser.getName();

                // IF START OF TAG, CHECK IF ITEM
                if (eventType == XmlPullParser.START_TAG) {

                    if (tagname.equals("item")) {
                        // INITIALISE THE EARTHQUAKE
                        currentEarthquake = new Earthquake();
                    }

                } else if (eventType == XmlPullParser.TEXT) {

                    // GET THE ACTUAL TEXT HERE
                    text = parser.getText();

                } else if (eventType == XmlPullParser.END_TAG) {

                    // REACHED THE END OF THE TAG - ADD THE PROPERTIES


                    if (tagname.equalsIgnoreCase("item")) {

                        earthquakes.add(currentEarthquake);

                    } else if (tagname.equalsIgnoreCase("link")) {

                        if (currentEarthquake != null) {

                            // SET LINK
                            currentEarthquake.setLink(text);
                        }

                    } else if (tagname.equalsIgnoreCase("description") && currentEarthquake != null) {

                        // PARSE THE DESCRIPTION STRING TO GET ALL THE TAGS
                        String[] elements;
                        elements = text.split("([A-Za-z\\/ ]+?):");

                        // 0 BLANK
                        // 1 DATE

                        // PARSE THE DATE INTO A DATE OJBECT
                        String pattern = "EEE, dd MMM yyyy kk:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        String dateString = elements[1].replace(" ;", "").trim();
                        currentEarthquake.setDate(simpleDateFormat.parse(dateString));

                        // GET THE LOCATION NAME
                        String locname = elements[2].replace(",",", ");
                        // 2 LOCATION
                        Location loc = new Location(locname, elements[3]);
                        currentEarthquake.setLocation(loc);

                        // 4 DEPTH - STRIP OUT ANY ADDITIONAL STRINGS e.g. km ;
                        currentEarthquake.setDepth(Integer.parseInt(elements[4].replace(" km ;", "").trim()));
                        // 5 Magnitude
                        currentEarthquake.setMagnitude(Double.parseDouble(elements[5].trim()));


                    } else if (parser.getName().equals("category") && currentEarthquake != null) {

                        currentEarthquake.setCategory(text);

                    }

                }

                eventType = parser.nextToken();
            }

        } catch (XmlPullParserException e) {
            status = false;
            e.printStackTrace();
        } catch (ParseException e) {
            status = false;
            Log.e(TAG, "Error Parsing Date: "+e.getMessage());
        }
        catch (IOException e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }

}
