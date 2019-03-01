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


public class MainActivity extends AppCompatActivity implements DownloadData.OnDownloadComplete {

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
//        downloadUrl(urlSource);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadData downloadData = new DownloadData(this);
        downloadData.execute("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if (status == DownloadStatus.OK) {

            Log.d(TAG, "onDownloadComplete: STATUS IS " + status.toString());

            ParseEarthquakes parseEarthquakes = new ParseEarthquakes();
            parseEarthquakes.parse(data);
            earthquakes = parseEarthquakes.getEarthquakes();
            Log.d(TAG, "onDownloadComplete: RETURNED " + earthquakes.size() + " earthquakes");

            // CREATE AN INSTANCE OF THE NEW CUSTOM FEED ADAPTER AND SET THE SOURCE
            EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(MainActivity.this, R.layout.list_earthquake, parseEarthquakes.getEarthquakes());
            earthquakeList.setAdapter(earthquakeListAdapter);

            // CREATE A DUMMY TOAST ITEM WHEN SOMEONE CLICKS
            earthquakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, "The depth for Earthquake " + position + " was " + earthquakes.get(position).getDepth() + "km", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Log.e(TAG, "onDownloadComplete: Something went wrong" + status.toString());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // CHECK TO SEE IF THERE ARE EARTHWUAKES AND SAVE
        // TODO: FIND A WAY TO PUT OBJECTS INTO THE BUNDLE
        super.onSaveInstanceState(outState);
    }

}