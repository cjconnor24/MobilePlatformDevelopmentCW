package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

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
        mEarthquakes = (List<Earthquake>) getIntent().getSerializableExtra("earthquakes");
        if (mEarthquakes != null) {
            Log.d(TAG, "onCreate: THERE ARE EARTHQIAKES" + mEarthquakes.size());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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

        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));
    }
}
