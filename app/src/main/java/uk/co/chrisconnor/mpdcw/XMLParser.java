package uk.co.chrisconnor.mpdcw;

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
    private String text;

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

                if (eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    System.out.println("Start tag:" + parser.getName());
                    if(parser.getName().equals("item")){
                        earthquake = new Earthquake();
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    System.out.println("Text " + parser.getText());
                } else if (eventType == XmlPullParser.END_TAG) {
                    System.out.println("End tag:" + parser.getName());
                }

//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        if (tagname.equalsIgnoreCase("item")) {
//                            // create a new instance of Earthquake
//                            earthquake = new Earthquake();
//                        }
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        text = parser.getText();
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        if (tagname.equalsIgnoreCase("item")) {
//                            // add employee object to list
////                            earthquakes.add(earthquake);
//                        } else if (tagname.equalsIgnoreCase("description")) {
////                            System.out.println("Description: "+text);
////                            earthquake.setDescription(text);
//                        } else if (tagname.equalsIgnoreCase("link")) {
//                            System.out.println("LINK: "+text);
////                            earthquake.setLink("http://www.google.com");
//                        } else if (tagname.equalsIgnoreCase("pubDate")) {
//
//                            earthquake.setPublishedDateTime(text);
//                        } else if (tagname.equalsIgnoreCase("category")) {
//
//                            earthquake.setCategory(text);
//                        }
//                        break;
//
//                    default:
//                        break;
//                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return earthquakes;
    }

}
