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
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;


public class MainActivity extends BaseActivity implements DownloadData.OnDownloadComplete {

    private static final String TAG = "MainActivity";
    private TextView rawDataDisplay;
    private Button startButton;



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


        // TODO: THIS WILL BE REMOVED - ONLY FOR TESTING
        try {
            Button bottomMenu = (Button) findViewById(R.id.viewBottomNav);
            bottomMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), MainNavigation.class);
                    startActivity(i);
                }
            });
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }


        // EARTHQUAKE LIST
//        TextView currentLocation = (TextView) findViewById(R.id.currentLocation);
        earthquakeList = (ListView) findViewById(R.id.earthquakeList);

        if (findViewById(R.id.top) != null) {

            landscapeMode = true;

        } else {

            Button viewMap = (Button) findViewById(R.id.viewMap);
            viewMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), EarthquakeMap.class);
                    i.putExtra(EARTHQUAKE_TRANSFER, (Serializable) earthquakes);
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

//            EarthquakeListFragment earthquakeListFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>)earthquakes);
//            FragmentManager f = getSupportFragmentManager();
//            FragmentTransaction t = f.beginTransaction();
//            t.add(R.id.container, earthquakeListFragment).commit();

            // CREATE A DUMMY TOAST ITEM WHEN SOMEONE CLICKS
            earthquakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Earthquake e = earthquakes.get(position);

                    if (landscapeMode) {

//                        EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
//
//                        Bundle b = new Bundle();
//                        b.putSerializable(EARTHQUAKE_TRANSFER,e);
//                        fragment.setArguments(b);

                        XEarthquakeDetailFragment xEarthquakeDetailFragment = XEarthquakeDetailFragment.newInstance(e);
                        FragmentManager f = getSupportFragmentManager();
                        FragmentTransaction transaction = f.beginTransaction();
                        transaction.add(R.id.bottom, xEarthquakeDetailFragment).commit();

//                        EarthquakeListFragment earthquakeListFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
//                        FragmentManager f = getSupportFragmentManager();
//                        FragmentTransaction t = f.beginTransaction();
//                        t.add(R.id.container, earthquakeListFragment).commit();

                        // JUST FOR TESTING
                        XEarthquakeMap xEarthquakeMap = XEarthquakeMap.newInstance(e);
//                        FragmentManager f = getSupportFragmentManager();
                        FragmentTransaction mapTransaction = f.beginTransaction();
                        mapTransaction.replace(R.id.top, xEarthquakeMap).commit();

//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, fragment)
//                                .commit();

                    } else {

                        Intent i = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
                        i.putExtra(EARTHQUAKE_TRANSFER, e);
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

//    @Override
//    public void onListFragmentInteraction(Earthquake item) {
//        Toast.makeText(this, item.getLocation().getName(), Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "onListFragmentInteraction: THIS WAS CLICKED" + item.toString());
//    }
}