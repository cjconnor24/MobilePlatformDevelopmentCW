/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * Earthquake detail activity creates view with details and map
 */
public class EarthquakeDetailActivity extends BaseActivity {

    private static final String TAG = "EarthquakeDetailActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // GET THE PASSED EARTHQUAKE TO DISPLAY
        Earthquake e = (Earthquake) getIntent().getSerializableExtra(EARTHQUAKE_TRANSFER);

        // IF ITS NULL
        if (savedInstanceState == null) {

            // CREATE AND SETUP THE MAP AND DETAIL FRAGMENTS
            FragmentManager fragmentManager = getSupportFragmentManager();

            EarthquakeDetailFragment earthquakeDetailFragment = EarthquakeDetailFragment.newInstance(e);
            FragmentTransaction detailTransaction = fragmentManager.beginTransaction();
            detailTransaction.replace(R.id.bottom, earthquakeDetailFragment).commit();

            EarthquakeMapFragment earthquakeMapFragment = EarthquakeMapFragment.newInstance(e);
            FragmentTransaction mapTransaction = fragmentManager.beginTransaction();
            mapTransaction.replace(R.id.top, earthquakeMapFragment).commit();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "BACK BUTTON PRESSED");
    }
}
