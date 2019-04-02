package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;
import uk.co.chrisconnor.mpdcw.models.Earthquake;
import uk.co.chrisconnor.mpdcw.models.Location;

public class EarthquakeMap extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "EarthquakeMap";

    private GoogleMap mMap;
    private List<Earthquake> mEarthquakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_map);

        Log.d(TAG, "onCreate: IS RUNNING");

        Intent i = getIntent();
        mEarthquakes = (List<Earthquake>) getIntent().getSerializableExtra(BaseActivity.EARTHQUAKE_TRANSFER);
        if (mEarthquakes != null) {
            Log.d(TAG, "onCreate: THERE ARE EARTHQIAKES" + mEarthquakes.size());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.d(TAG, "onMapReady: MAP IS READY");

        ArrayList<Marker> markers = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mEarthquakes != null && mEarthquakes.size() > 0) {

            for (Earthquake e : mEarthquakes) {

                // ADD MARKER TO THE MAP
                markers.add(mMap.addMarker(
                        new MarkerOptions()
                                .position(new LatLng(e.getLocation().getLat(), e.getLocation().getLon()))
                                .title(e.getLocation().getName())
                                .snippet(PrettyDate.getTimeAgo(e.getDate()))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                ));

                builder.include(new LatLng(e.getLocation().getLat(), e.getLocation().getLon()));


            }

        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "onMarkerClick: " + marker.toString());

                Log.d(TAG, "onMarkerClick: " + marker.getId());
                Toast.makeText(EarthquakeMap.this, "You clicked on " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));
    }
}
