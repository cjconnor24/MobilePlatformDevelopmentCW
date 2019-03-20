package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

                    if(mFragment.getClass() != EarthquakeListFragment.class) {
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

                    if(mFragment.getClass() != XEarthquakeMap.class) {

                        mFragment = XEarthquakeMap.newInstance(earthquakes.get(0));
                        FragmentTransaction mapTransaction = mFragmentManager.beginTransaction();
                        mapTransaction.addToBackStack(null);
                        mapTransaction.replace(R.id.fragment_frame, mFragment).commit();


                        return true;
                    } else {
                        Toast.makeText(MainNavigation.this, "You are already viewing the map", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                case R.id.navigation_search:

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

//        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(earthquakes == null) {
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
            Toast.makeText(this, "THEY DOWNLOADED" + earthquakes.size(), Toast.LENGTH_SHORT).show();

            // CREATE AN INSTANCE OF THE NEW CUSTOM FEED ADAPTER AND SET THE SOURCE
//            EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(MainActivity.this, R.layout.list_earthquake, parseEarthquakes.getEarthquakes());
//            earthquakeList.setAdapter(earthquakeListAdapter);

            if(mFragment == null) {
                mFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction t = mFragmentManager.beginTransaction();
                t.add(R.id.fragment_frame, mFragment).commit();
            } else {
                Log.d(TAG, "onDownloadComplete: onResume...?");
            }


            // CREATE A DUMMY TOAST ITEM WHEN SOMEONE CLICKS
//            earthquakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                    Earthquake e = earthquakes.get(position);
//
//                    if (landscapeMode) {

//                        EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
//
//                        Bundle b = new Bundle();
//                        b.putSerializable(EARTHQUAKE_TRANSFER,e);
//                        fragment.setArguments(b);
//
//                        XEarthquakeDetailFragment xEarthquakeDetailFragment = XEarthquakeDetailFragment.newInstance(e);
//                        FragmentManager f = getSupportFragmentManager();
//                        FragmentTransaction transaction = f.beginTransaction();
//                        transaction.add(R.id.bottom, xEarthquakeDetailFragment).commit();

//                        EarthquakeListFragment earthquakeListFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
//                        FragmentManager f = getSupportFragmentManager();
//                        FragmentTransaction t = f.beginTransaction();
//                        t.add(R.id.container, earthquakeListFragment).commit();

                        // JUST FOR TESTING
//                        XEarthquakeMap xEarthquakeMap = XEarthquakeMap.newInstance(e);
//                        FragmentManager f = getSupportFragmentManager();
//                        FragmentTransaction mapTransaction = f.beginTransaction();
//                        mapTransaction.replace(R.id.top, xEarthquakeMap).commit();

//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, fragment)
//                                .commit();

//                    } else {
//
//                        Intent i = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
//                        i.putExtra(EARTHQUAKE_TRANSFER, e);
//                        startActivity(i);
//
//                    }
//
//                }
//            });


        } else {
            Log.e(TAG, "onDownloadComplete: Something went wrong" + status.toString());
        }

    }

    @Override
    public void onListFragmentInteraction(Earthquake item) {
        Toast.makeText(this, "Something clicked..." + item.getLocation().getName(), Toast.LENGTH_SHORT).show();
    }
}
