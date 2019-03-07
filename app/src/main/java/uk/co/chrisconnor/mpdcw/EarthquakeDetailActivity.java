package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import uk.co.chrisconnor.mpdcw.models.Earthquake;
import uk.co.chrisconnor.mpdcw.ui.earthquakedetail.EarthquakeDetailFragment;

public class EarthquakeDetailActivity extends AppCompatActivity {

    private static final String TAG = "EarthquakeDetailActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_detail_activity);

        Log.d(TAG, "onCreate: STARTS");

        Intent i = getIntent();
        Earthquake e = (Earthquake) getIntent().getSerializableExtra("earthquake");

        if(e != null){
            Log.d(TAG, "The Earthquake is: "+ e.toString());

//            TextView title = (TextView)findViewById(R.id.location_heading);
//            title.setText(e.getLocation().getName());
//            TextView subtitle = (TextView)findViewById(R.id.datetime_heading);
//            subtitle.setText(PrettyDate.getTimeAgo(e.getDate()));

        }


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EarthquakeDetailFragment.newInstance())
                    .commitNow();
        }
    }
}
