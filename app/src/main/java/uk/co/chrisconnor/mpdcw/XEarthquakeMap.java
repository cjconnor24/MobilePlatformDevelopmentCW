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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XEarthquakeMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XEarthquakeMap extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "XEarthquakeMap";
    private static final String EARTHQUAKE = "earthquake";
    private static final String EARTHQUAKE_LIST = "earthquake_list";

    private Earthquake mEarthquake;
    private ArrayList<Earthquake> mEarthquakeList;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private boolean multipleMarkers = false;


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

    /**
     * Overloaded method for creating a new instance of the fragment with a list of earthquakes
     * @param earthquake_list
     * @return
     */
    public static XEarthquakeMap newInstance(ArrayList<Earthquake> earthquake_list) {

        XEarthquakeMap fragment = new XEarthquakeMap();
        Bundle args = new Bundle();
        args.putSerializable(EARTHQUAKE_LIST, earthquake_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MAKE SURE THERE ARE PASSED DATA
        if (getArguments() != null) {

            // CHECK TO SEE IF IT'S SINGLE EARTHQUAKE
            if(getArguments().getSerializable(EARTHQUAKE) != null){

                mEarthquake = (Earthquake) getArguments().getSerializable(EARTHQUAKE);

                // OR IF ITS A LIST
            } else if(getArguments().getSerializable(EARTHQUAKE_LIST) != null){

                mEarthquakeList = (ArrayList<Earthquake>) getArguments().getSerializable(EARTHQUAKE_LIST);
                multipleMarkers = true;

            }


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

//        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));
//        if(success){
//            Log.d(TAG, "onMapReady: SHOULD HAVE STYLES");
//        } else {
//            Log.e(TAG, "onMapReady: STRING STYLES DID NOT WORK");
//        }
        mMap = googleMap;


        if(!multipleMarkers) {
            LatLng location = new LatLng(mEarthquake.getLocation().getLat(), mEarthquake.getLocation().getLon());
            mMap.addMarker(new MarkerOptions().position(location).title(mEarthquake.getLocation().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8f));
        } else {

            // LOOP THROUGH MULTIPLE MARKERS
            ArrayList<Marker> markers = new ArrayList<>();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            if (mEarthquakeList != null && mEarthquakeList.size() > 0) {

                for (Earthquake e : mEarthquakeList) {

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


//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    Log.d(TAG, "onMarkerClick: " + marker.toString());
//
//                    Log.d(TAG, "onMarkerClick: " + marker.getId());
//                    Toast.makeText(EarthquakeMap.this, "You clicked on " + marker.getTitle(), Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            });

            LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));

        }


    }
}
