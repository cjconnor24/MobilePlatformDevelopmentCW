/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Chris Connor
// Student ID           S1715477
// Programme of Study   Computing (BSc Hons)
//
package uk.co.chrisconnor.mpdcw;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView rawDataDisplay;
    private Button startButton;

    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private ListView earthquakeList;
    List<Earthquake> earthquakes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the raw links to the graphical components
//        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
//        startButton = (Button) findViewById(R.id.startButton);

        // EARTHQUAKE LIST
        earthquakeList = (ListView) findViewById(R.id.earthquakeList);


        // DOWNLOAD THE DATA THEN DISPLAY FURTHER DOWN INTO THE VIEW
        downloadUrl(urlSource);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // CHECK TO SEE IF THERE ARE EARTHWUAKES AND SAVE
        // TODO: FIND A WAY TO PUT OBJECTS INTO THE BUNDLE
        super.onSaveInstanceState(outState);
    }

    // DOWNLOAD THE XML DATA FROM THE PASSED URL
    private void downloadUrl(String feedUrl) {

        Log.d(TAG, "downloadUrl: starting Async Task");
        DownloadData downloadData = new DownloadData();
        downloadData.execute(feedUrl);
        Log.d(TAG, "downloadUrl: done");

    }



    // ASYNC CLASS TASK TO DOWNLOAD DATA ON SEPARATE THREAD
    private class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            // GET THE XML DATA PARSED TO OBJECTS
            final ParseEarthquakes parseEarthquakes = new ParseEarthquakes();
            parseEarthquakes.parse(s);

            earthquakes = parseEarthquakes.getEarthquakes();

            // CREATE AN INSTANCE OF THE NEW CUSTOM FEED ADAPTER AND SET THE SOURCE
            EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(MainActivity.this, R.layout.list_earthquake, parseEarthquakes.getEarthquakes());
            earthquakeList.setAdapter(earthquakeListAdapter);

            // CREATE A DUMMY TOAST ITEM WHEN SOMEONE CLICKS
            earthquakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "The depth for Earthquake " + position + " was " + parseEarthquakes.getEarthquakes().get(position).getDepth() + "km", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        /**
         * Open up the connection
         *
         * @param urlPath
         * @return
         */
        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();

                Log.d(TAG, "downloadXML: ** Downloaded the XML DATA from URL ** " + new Date().toString());
                Log.d(TAG, "downloadXML: The response code was " + response);


                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead;
                char[] inputBuffer = new char[500]; // CREATE A BUFFER

                // START TO LOOP
                while (true) {

                    // ADD TO BUFFER
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break; // BREAK IF COMPLETE
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();

            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL" + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data:" + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: cannot access internet: Needs permission? " + e.getMessage());
            }


            return null;

        }

    }

}