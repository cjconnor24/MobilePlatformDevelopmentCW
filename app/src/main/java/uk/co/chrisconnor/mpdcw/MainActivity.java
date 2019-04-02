package uk.co.chrisconnor.mpdcw;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class MainActivity extends BaseActivity implements DownloadData.OnDownloadComplete, EarthquakeListFragment.OnListFragmentInteractionListener, SearchFrament.OnSearchFragmentInteractionListener {


    private static final String TAG = "MainActivity";
    private List<Earthquake> earthquakes;
    //            private String urlSource = "http://quakes.bgs.ac.uk/feeds/WorldSeismology.xml";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private EarthquakeDatabase mdb;

    // LIST FRAGMENTS
    private Fragment dashboardFragment;
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

                    Toast.makeText(MainActivity.this, "You Clicked DASHBOARD", Toast.LENGTH_SHORT).show();
                    mFragment = dashboardFragment;
                    break;
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // INITIALISE THE DB
        mdb = new EarthquakeDatabase(this);
        mdb.open();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: " + item);

        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

            EarthquakeDatabase.mEarthquakeDao.addEarthquakes(earthquakes);


            // INITIALISE THE FRAGMENTS
            listFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
            mapFragment = XEarthquakeMap.newInstance((ArrayList<Earthquake>) earthquakes);
            searchFragment = SearchFrament.newInstance("one", "two");
            dashboardFragment = DashboardFragment.newInstance("one", "two");


            // IF ITS BLANK, DISPLAY THE DASHBOARD
            if (mFragment == null) {
                mFragment = dashboardFragment;
                mFragmentManager.beginTransaction().replace(R.id.fragment_frame, mFragment).commit();
            }

        } else {
            Log.e(TAG, "onDownloadComplete: Something went wrong " + status.toString());

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("There was an issue downloading the data. Please try again shortly.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();

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
    public void onBackPressed() {
        super.onBackPressed();
        mFragmentManager.popBackStack();
        Toast.makeText(this, "BACK WAS PRESSED", Toast.LENGTH_SHORT).show();
    }

    /**
     * This callback function is triggered when search results are returned from the search fragment
     * @param earthquakes
     */
    @Override
    public void onSearchResultsReturned(List<Earthquake> earthquakes) {

        // VAR TO STORE THE RESULTS FRAG TAG
        String SEARCH_RESULTS = "search_results";

        // CHECK TO SEE IF RESULTS WERE RETURNED
        if (earthquakes == null) {

            // CLEAR THE FRAGMENT IF ANY?
            if (mFragmentManager.findFragmentByTag(SEARCH_RESULTS) != null) {

                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.remove(mFragmentManager.findFragmentByTag(SEARCH_RESULTS)).commit();

            }

        } else {

            // OVERLAY THE RESULTS IN LIST FRAGMENT
            Fragment searchResults = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
            mFragment = searchResults;
            FragmentTransaction t = mFragmentManager.beginTransaction();
            t.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_left);


            // DISPLAY THE RESULTS
            if (findViewById(R.id.searchResultsLandscape) == null) {
                t.addToBackStack(null);
                t.add(R.id.fragment_frame, mFragment, SEARCH_RESULTS).commit();
            } else {
                t.replace(R.id.searchResultsLandscape, searchResults, SEARCH_RESULTS).commit();
            }

        }


    }


    /**
     * Close to database on destroy
     */
    @Override
    protected void onDestroy() {
        mdb.close();
        super.onDestroy();
    }
}
