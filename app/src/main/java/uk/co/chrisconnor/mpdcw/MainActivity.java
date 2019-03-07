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


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;


public class MainActivity extends AppCompatActivity implements DownloadData.OnDownloadComplete {

    private static final String TAG = "MainActivity";
    private TextView rawDataDisplay;
    private Button startButton;

    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
//    private String urlSource = "http://quakes.bgs.ac.uk/feeds/WorldSeismology.xml";
    private ListView earthquakeList;
    List<Earthquake> earthquakes = null;

    private boolean landscapeMode = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the raw links to the graphical components
//        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
//        startButton = (Button) findViewById(R.id.startButton);



        // EARTHQUAKE LIST
//        TextView currentLocation = (TextView) findViewById(R.id.currentLocation);
        earthquakeList = (ListView) findViewById(R.id.earthquakeList);

        if(findViewById(R.id.container) != null){

            landscapeMode = true;

        } else {

            Button viewMap = (Button)findViewById(R.id.viewMap);
            viewMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), EarthquakeMap.class);
                    i.putExtra("earthquakes",(Serializable)earthquakes);
                    startActivity(i);
                }
            });

        }


        // DOWNLOAD THE DATA THEN DISPLAY FURTHER DOWN INTO THE VIEW
//        downloadUrl(urlSource);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DownloadData downloadData = new DownloadData(this);
        downloadData.execute(urlSource);
    }

    /**
     * When download completes, send it across to the parser to return the Earthquakes
     *
     * @param data   XML data coming back from DataDownloader
     * @param status status of the responses
     */
    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if (status == DownloadStatus.OK) {

            Log.d(TAG, "onDownloadComplete: STATUS IS " + status.toString());

            // TODO: MAYBE MAKE THE PARSER ASYNC?
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

                    Earthquake e = earthquakes.get(position);

                    if(landscapeMode){

                        EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();

                        Bundle b = new Bundle();
                        b.putSerializable("earthquake",e);
                        fragment.setArguments(b);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();

                    } else {

                        Intent i = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
                        i.putExtra("earthquake",e);
                        startActivity(i);

                    }

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