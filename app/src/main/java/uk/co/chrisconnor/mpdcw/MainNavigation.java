package uk.co.chrisconnor.mpdcw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class MainNavigation extends BaseActivity implements DownloadData.OnDownloadComplete {

    private static final String TAG = "MainNavigation";
    private TextView mTextMessage;
    private Fragment mFragment;
    private List<Earthquake> earthquakes = null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
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
        mFragment = new EarthquakeDetailFragment();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_frame,mFragment,"BLANK_FRAGMENT").commit();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if (status == DownloadStatus.OK) {

            Log.d(TAG, "onDownloadComplete: STATUS IS " + status.toString());

            // TODO: MAYBE MAKE THE PARSER ASYNC?
//            RetrieveEarthquakes retrieveEarthquakes = new RetrieveEarthquakes();
//            retrieveEarthquakes.parse(data);
//            earthquakes = retrieveEarthquakes.getEarthquakes();
            Log.d(TAG, "onDownloadComplete: RETURNED " + earthquakes.size() + " earthquakes");


        } else {
            Log.e(TAG, "onDownloadComplete: Something went wrong" + status.toString());
        }
        
    }
}
