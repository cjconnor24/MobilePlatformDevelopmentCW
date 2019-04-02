package uk.co.chrisconnor.mpdcw;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeDetailActivity extends BaseActivity {

    private static final String TAG = "EarthquakeDetailActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_detail_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Earthquake e = (Earthquake) getIntent().getSerializableExtra(EARTHQUAKE_TRANSFER);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            XEarthquakeDetailFragment xEarthquakeDetailFragment = XEarthquakeDetailFragment.newInstance(e);
            FragmentTransaction detailTransaction = fragmentManager.beginTransaction();
            detailTransaction.replace(R.id.bottom, xEarthquakeDetailFragment).commit();

            XEarthquakeMap xEarthquakeMap = XEarthquakeMap.newInstance(e);
            FragmentTransaction mapTransaction = fragmentManager.beginTransaction();
            mapTransaction.replace(R.id.top, xEarthquakeMap).commit();

        }

    }

}
