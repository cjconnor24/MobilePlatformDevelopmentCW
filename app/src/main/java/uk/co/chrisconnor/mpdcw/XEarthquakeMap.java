package uk.co.chrisconnor.mpdcw;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XEarthquakeMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XEarthquakeMap extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "XEarthquakeMap";
    private static final String EARTHQUAKE = "earthquake";
    private Earthquake mEarthquake;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;


    public XEarthquakeMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param earthquake Earthquake to display.
     * @return A new instance of fragment XEarthquakeMap.
     */
    public static XEarthquakeMap newInstance(Earthquake earthquake) {

        Log.d(TAG, "newInstance: THIS FIRES");
        
        XEarthquakeMap fragment = new XEarthquakeMap();
        Bundle args = new Bundle();
        args.putSerializable(EARTHQUAKE, earthquake);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEarthquake = (Earthquake) getArguments().getSerializable(EARTHQUAKE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: THIS FIRES ALSO");
        View view = inflater.inflate(R.layout.earthquake_map_fragment, container, false);

//        view.findViewById(R.id.map);
        mMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
//        mMapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.map);
//        mMapFragment = (SupportMapFragment) getFragmentManager().find;
        if(mMapFragment != null){
            Log.d(TAG, "onCreateView: IS THE MAP NOT NULL?");
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mMapFragment).commit();
//
        }
//
        try {
            mMapFragment.getMapAsync(this);
        } catch (NullPointerException e){
            Log.e(TAG, "onCreateView: uh oh...mMapFragment is null?");
        }


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Log.d(TAG, "onMapReady: Google Map is ready anyway 😂");
        LatLng location = new LatLng(mEarthquake.getLocation().getLat(), mEarthquake.getLocation().getLon());
        mMap.addMarker(new MarkerOptions().position(location).title(mEarthquake.getLocation().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8f));



    }
}
