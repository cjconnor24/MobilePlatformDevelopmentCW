package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class MainNavigation extends BaseActivity implements DownloadData.OnDownloadComplete, EarthquakeListFragment.OnListFragmentInteractionListener, SearchFrament.OnSearchFragmentInteractionListener {

//    private TextView mTextMessage;

    private static final String TAG = "MainNavigation";
    private List<Earthquake> earthquakes;
    //            private String urlSource = "http://quakes.bgs.ac.uk/feeds/WorldSeismology.xml";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private EarthquakeDatabase mdb;

    // LIST FRAGMENTS
    private Fragment dashFragment;
    private Fragment listFragment;
    private Fragment mapFragment;
    private Fragment searchFragment;
    private Fragment mFragment;

    // DETECT LANDSCAPE MODE
    private boolean landscapeMode = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment previous = mFragment;

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mFragment = dashFragment;
                    return true;
                case R.id.navigation_list:

                    mFragment = listFragment;
                    break;


                case R.id.navigation_map:

                    mFragment = mapFragment;
                    break;
//
                case R.id.navigation_search:

                    mFragment = searchFragment;
                    break;

                default:
                    mFragment = listFragment;
                    break;
            }
            mFragmentManager.beginTransaction().replace(R.id.fragment_frame, mFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // INITIALISE THE DB
        mdb = new EarthquakeDatabase(this);
        mdb.open();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: FRAGMENTS ON START");
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

            EarthquakeDatabase.mEarthquakeDao.addEarthquakes(earthquakes);

            dashFragment = DashboardFragment.newInstance();
            listFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
            mapFragment = XEarthquakeMap.newInstance((ArrayList<Earthquake>) earthquakes);
            searchFragment = SearchFrament.newInstance("one", "two");

//            mFragment = dashFragment;
//            mFragmentManager.beginTransaction().replace(R.id.fragment_frame, mFragment).commit();

            if (mFragment == null) {
                mFragment = dashFragment;
                mFragmentManager.beginTransaction().replace(R.id.fragment_frame, mFragment).commit();
//                mFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) EarthquakeDatabase.mEarthquakeDao.fetchAllEarthquakes());
//                mFragmentManager = getSupportFragmentManager();
//                FragmentTransaction t = mFragmentManager.beginTransaction();
//                t.replace(R.id.fragment_frame, mFragment).commit();
            } else {
                Log.d(TAG, "onDownloadComplete: onResume...?");
            }

        } else {
            Log.e(TAG, "onDownloadComplete: Something went wrong" + status.toString());
        }

    }

    /**
     * Launches new activity based on the earthquake clicked in the list
     *
     * @param item Earthquake item clicked
     */
    @Override
    public void onListEarthquakeListItemClick(Earthquake item) {

        Toast.makeText(this, "Something clicked..." + item.getLocation().getName(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
        i.putExtra(EARTHQUAKE_TRANSFER, item);
        startActivity(i);


    }

    @Override
    public void onSearchResultsReturned(List<Earthquake> earthquakes) {

        String SEARCH_RESULTS = "search_results";

        List<Fragment> fragList = mFragmentManager.getFragments();
        fragList.size();


        if (earthquakes == null) {

            // CLEAR THE FRAGMENT IF ANY?
            if (mFragmentManager.findFragmentByTag(SEARCH_RESULTS) != null) {

                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.remove(mFragmentManager.findFragmentByTag(SEARCH_RESULTS)).commit();

            }

        } else {

            Toast.makeText(this, "Results were returned to main. Count: " + earthquakes.size(), Toast.LENGTH_SHORT).show();

            Fragment searchResults = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
            mFragment = searchResults;
            FragmentTransaction t = mFragmentManager.beginTransaction();
            t.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_left);


            if (findViewById(R.id.searchResultsLandscape) == null) {
                t.addToBackStack(null);
                t.add(R.id.fragment_frame, mFragment, SEARCH_RESULTS).commit();
            } else {
                t.replace(R.id.searchResultsLandscape, searchResults, SEARCH_RESULTS).commit();
            }

        }


    }


    @Override
    protected void onDestroy() {
        mdb.close();
        super.onDestroy();

    }
}
