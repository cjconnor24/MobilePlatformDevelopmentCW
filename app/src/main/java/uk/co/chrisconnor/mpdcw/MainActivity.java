package uk.co.chrisconnor.mpdcw;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class MainActivity extends BaseActivity implements DownloadData.OnDownloadComplete, EarthquakeListFragment.OnListFragmentInteractionListener, SearchFrament.OnSearchFragmentInteractionListener {


    private static final String TAG = "MainActivity";


    //private String urlSource = "http://quakes.bgs.ac.uk/feeds/WorldSeismology.xml";
    //private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private String urlSource = "http://earthquake.chrisconnor.co.uk/data.xml";

    // FRAGMENT MANAGER AND DB PLACEHOLDERS
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private EarthquakeDatabase mdb;
    private List<Earthquake> earthquakes;

    // LIST FRAGMENTS
    private Fragment dashboardFragment;
    private Fragment listFragment;
    private Fragment mapFragment;
    private Fragment searchFragment;
    private Fragment mFragment;

    // CHANGE THE FRAGMENT ON SELECTION FROM THE BOTTOM MENU
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mFragment = dashboardFragment;
                    break;
                case R.id.navigation_list:
                    mFragment = listFragment;
                    break;
                case R.id.navigation_map:
                    mFragment = mapFragment;
                    break;
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // INITIALISE THE BOTTOM NAV AND SETUP THE LISTENERS
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // INITIALISE THE DB AND FETCH RESULTS
        mdb = new EarthquakeDatabase(this);
        mdb.open();
        earthquakes = EarthquakeDatabase.mEarthquakeDao.fetchAllEarthquakes();

        // INITIALISE THE FRAGMENTS
        initialiseFragments();

        // IF ITS BLANK, DISPLAY THE DASHBOARD
        if (mFragment == null) {
            mFragment = dashboardFragment;
            mFragmentManager.beginTransaction().replace(R.id.fragment_frame, mFragment).commit();
        }

    }

    /**
     * Initialise the fragments contained within the activity
     */
    private void initialiseFragments() {
        listFragment = EarthquakeListFragment.newInstance((ArrayList<Earthquake>) earthquakes);
        mapFragment = EarthquakeMapFragment.newInstance((ArrayList<Earthquake>) earthquakes);
        searchFragment = SearchFrament.newInstance("one", "two");
        dashboardFragment = DashboardFragment.newInstance();
    }

    /**
     * Inflate the menu
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handle toolbar option clicks including home button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // DISPLAY THE BACK BUTTON
                onBackPressed();
                upDateActionBar(false);
                break;
            case R.id.menu_refresh:
                // DOWNLOAD DATA FROM API
                downloadData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Triggers when activityResumes
     */
    @Override
    protected void onResume() {
        super.onResume();

        // IF NO EARTHQUAKES, DOWNLOAD FROM API
        if (earthquakes == null || earthquakes.size() == 0) {
            downloadData();
        }
    }

    /**
     * Download data from api
     */
    private void downloadData() {

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

            Toast t = Toast.makeText(this, "Earthquake data was refreshed", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            t.show();

            // TODO: MAYBE MAKE THE PARSER ASYNC?
            // PARSE EARTHQUAKES
            ParseEarthquakes parseEarthquakes = new ParseEarthquakes();
            parseEarthquakes.parse(data);
            earthquakes = parseEarthquakes.getEarthquakes();

            // STORE THE DATA IN THE DB
            EarthquakeDatabase.mEarthquakeDao.addEarthquakes(earthquakes);

            // REINITIALISE THE FRAGMENT
            initialiseFragments();

        } else {

            // IF NO EARTHQUAKES, INFORM USER AND QUIT GRACEFULLY
            if (earthquakes == null || earthquakes.size() == 0) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Problem retreiving data");
                builder1.setMessage("There was an issue downloading the data and you currently have no saved data. Please try again shortly.");
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
            } else {

                Toast.makeText(this, "There was a problem getting live data. Will try again shortly.", Toast.LENGTH_LONG).show();

            }

        }

    }

    /**
     * Launches new activity based on the earthquake clicked in the list
     *
     * @param item Earthquake item clicked
     */
    @Override
    public void onListEarthquakeListItemClick(Earthquake item) {

        // CREATE NEW DETAIL INTENT AND LAUNCH
        Intent i = new Intent(getApplicationContext(), EarthquakeDetailActivity.class);
        i.putExtra(EARTHQUAKE_TRANSFER, item);
        startActivity(i);

    }

    /**
     * When back button pressed, pop fragment from back stack
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragmentManager.popBackStack();
    }

    /**
     * This callback function is triggered when search results are returned from the search fragment
     *
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

            // DISPLAY THE BACK BUTTON
            upDateActionBar(true);
            t.addToBackStack(null);

            // DISPLAY THE RESULTS
            if (findViewById(R.id.searchResultsLandscape) == null) {

                t.add(R.id.fragment_frame, mFragment, SEARCH_RESULTS).commit();
            } else {
                t.replace(R.id.searchResultsLandscape, searchResults, SEARCH_RESULTS).commit();
            }

        }


    }

    /**
     * Update action bar to show / hide the back button
     *
     * @param show
     */
    private void upDateActionBar(boolean show) {

        if (show) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Search Results");
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
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
