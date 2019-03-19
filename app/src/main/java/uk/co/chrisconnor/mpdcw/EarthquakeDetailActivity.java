package uk.co.chrisconnor.mpdcw;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeDetailActivity extends BaseActivity {

    private static final String TAG = "EarthquakeDetailActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_detail_activity);


//        Intent i = getIntent();
        Earthquake e = (Earthquake) getIntent().getSerializableExtra(EARTHQUAKE_TRANSFER);


        if (savedInstanceState == null) {




            XEarthquakeDetailFragment xEarthquakeDetailFragment = XEarthquakeDetailFragment.newInstance(e);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.bottom, xEarthquakeDetailFragment).commit();




            XEarthquakeMap xEarthquakeMap = XEarthquakeMap.newInstance(e);
            FragmentTransaction mapTransaction = fragmentManager.beginTransaction();
            mapTransaction.replace(R.id.top, xEarthquakeMap).commit();
//            FragmentManager f = getSupportFragmentManager();
//            FragmentTransaction transaction = f.beginTransaction();
//            transaction.replace(R.id.container, xEarthquakeMap).commit();

//            EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();
//
//            Bundle b = new Bundle();
//            b.putSerializable(EARTHQUAKE_TRANSFER, e);
//
//            fragment.setArguments(b);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, fragment)
//                    .commitNow();
        }

    }

}
