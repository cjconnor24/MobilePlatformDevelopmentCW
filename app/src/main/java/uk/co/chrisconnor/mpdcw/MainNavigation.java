package uk.co.chrisconnor.mpdcw;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDAO;
import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class MainNavigation extends BaseActivity implements DownloadData.OnDownloadComplete, EarthquakeListFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    private Fragment mFragment;
    private static final String TAG = "MainNavigation";
    private List<Earthquake> earthquakes;
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private FragmentManager mFragmentManager = getSupportFragmentManager();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_dashboard:
////                    mTextMessage.setText(R.string.title_home);
//                    return true;
                case R.id.navigation_list:

                    if (mFragment.getClass() != EarthquakeListFragment.class) {
                        mFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
                        FragmentTransaction listTransaction = mFragmentManager.beginTransaction();
                        listTransaction.addToBackStack(null);
                        listTransaction.replace(R.id.fragment_frame, mFragment).commit();
                        return true;
                    } else {
                        Toast.makeText(MainNavigation.this, "You're already viewing the list", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                case R.id.navigation_map:

                    if (mFragment.getClass() != XEarthquakeMap.class) {

                        mFragment = XEarthquakeMap.newInstance((ArrayList<Earthquake>) earthquakes);
                        FragmentTransaction mapTransaction = mFragmentManager.beginTransaction();
                        mapTransaction.addToBackStack(null);
                        mapTransaction.replace(R.id.fragment_frame, mFragment).commit();


                        return true;
                    } else {
                        Toast.makeText(MainNavigation.this, "You are already viewing the map", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                case R.id.navigation_search:

                    // TODO: REMOVE THIS - ONLY FOR TESTING TO TAKE ME BACK TO THE PREVIOUS MAIN ACTIVITY
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);




//        Log.d(TAG, "onCreate: " + e.toString());

//        if (e != null) {
//            if (earthquakeDAO.addEarthquake(e)) {
//                Log.d(TAG, "onCreate: EARTHQUAKE SAVED");
//            } else {
//                Log.e(TAG, "onCreate: EARTHQUAKE DIDNT SAVE");
//            }
//        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (earthquakes == null) {
            DownloadData downloadData = new DownloadData(this);
            downloadData.execute(urlSource);
        } else {
            Log.d(TAG, "onResume: shouldnt have redownloaded?");
        }
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

            Earthquake e = earthquakes.get(4);

            EarthquakeDatabase earthquakeDatabase = EarthquakeDatabase.getInstance(this);
            SQLiteDatabase db = earthquakeDatabase.getReadableDatabase();
            EarthquakeDAO earthquakeDAO = new EarthquakeDAO(db);


            
//            if(earthquakeDAO.addEarthquakes(earthquakes)){
//                Log.d(TAG, "onDownloadComplete: THIS SHOULD HAVE SAVED");
//            } else {
//                Log.d(TAG, "onDownloadComplete: THIS DID NOT SAVE");
//            }

            List<Earthquake> eqs = earthquakeDAO.fetchAllEarthquakes();
            if(eqs != null && eqs.size() > 0) {
                Log.d(TAG, "onDownloadComplete: " + eqs.toString());
            } else {
                Log.e(TAG, "onDownloadComplete: Uh oh...something went wrong with getting the eqs" );
            }


            if (mFragment == null) {
                mFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction t = mFragmentManager.beginTransaction();
                t.replace(R.id.fragment_frame, mFragment).commit();
            } else {
                Log.d(TAG, "onDownloadComplete: onResume...?");
            }

        } else {
            Log.e(TAG, "onDownloadComplete: Something went wrong" + status.toString());
        }

    }

    @Override
    public void onListEarthquakeListItemClick(Earthquake item) {

        Toast.makeText(this, "Something clicked..." + item.getLocation().getName(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
        i.putExtra(EARTHQUAKE_TRANSFER, item);
        startActivity(i);


    }
}
