package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeDetailActivity extends AppCompatActivity {

    private static final String TAG = "EarthquakeDetailActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_detail_activity);


//        Intent i = getIntent();
        Earthquake e = (Earthquake) getIntent().getSerializableExtra("earthquake");


        if (savedInstanceState == null) {

            EarthquakeDetailFragment fragment = new EarthquakeDetailFragment();

            Bundle b = new Bundle();
            b.putSerializable("earthquake", e);

            fragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }

    }

}
