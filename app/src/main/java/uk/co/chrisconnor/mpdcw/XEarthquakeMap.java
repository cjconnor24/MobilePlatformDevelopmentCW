package uk.co.chrisconnor.mpdcw;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;
import uk.co.chrisconnor.mpdcw.models.Earthquake;
import uk.co.chrisconnor.mpdcw.models.Location;

import static uk.co.chrisconnor.mpdcw.BaseActivity.EARTHQUAKE_TRANSFER;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XEarthquakeMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XEarthquakeMap extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "XEarthquakeMap";
    private static final String EARTHQUAKE = "earthquake";
    private static final String EARTHQUAKE_LIST = "earthquake_list";

    // TWO WAYS THE EARTHQUAKES WILL COME THROUGH
    private Earthquake mEarthquake;
    private ArrayList<Earthquake> mEarthquakeList;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    // MAP SPECIFIC VARIABLES
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private ClusterManager<Earthquake> mEarthquakeClusterManager;

    // STATE
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
     *
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
            if (getArguments().getSerializable(EARTHQUAKE) != null) {

                mEarthquake = (Earthquake) getArguments().getSerializable(EARTHQUAKE);

                // OR IF ITS A LIST
            } else if (getArguments().getSerializable(EARTHQUAKE_LIST) != null) {

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
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mMapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.map);
//        mMapFragment = (SupportMapFragment) getFragmentManager().find;
        if (mMapFragment != null) {
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
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: uh oh...mMapFragment is null?");
        }


        return view;
    }

    /**
     * Plot multiple earthquakes on the map.
     * @param mMap Map reference
     * @param earthquakes The earthquakes list to plot.
     */
    private void plotEarthquakes(GoogleMap mMap, ArrayList<Earthquake> earthquakes){

        // LOOP THROUGH MULTIPLE MARKERS
        ArrayList<Marker> markers = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mEarthquakeList != null && mEarthquakeList.size() > 0) {

            int i = 0;
            for (Earthquake e : mEarthquakeList) {

                // ADD MARKER TO THE MAP
//                markers.add(mMap.addMarker(
//                        createMarker(e)
//                ));
//                addClusterItem(e);
//                mEarthquakeClusterManager.addItem(e);

//                mEarthquakeClusterManager.getMarkerManager();

//                Marker m = mMap.addMarker(createMarker(e));
//                m.setVisible(false);
//                mHashMap.put(m,i);

                builder.include(new LatLng(e.getLocation().getLat(), e.getLocation().getLon()));

                i++;
            }

            mEarthquakeClusterManager.addItems(earthquakes);

        }

        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));

    }

    /**
     * Plot a single earthquake on the Map
     * @param mMap Map reference
     * @param earthquake to plot on the map
     */
    private void plotEarthquake(GoogleMap mMap, Earthquake earthquake){
        LatLng location = new LatLng(earthquake.getLocation().getLat(), earthquake.getLocation().getLon());
        mMap.addMarker(createMarker(earthquake));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 8f));
    }

    /**
     * Build up the marker options - title, snippet and icon. Reused for both modes
     * @param e Earthquake to build marker from
     * @return MarkerOptions of earthquake
     */
    private MarkerOptions createMarker(Earthquake e){
        return new MarkerOptions()
                .position(new LatLng(e.getLocation().getLat(), e.getLocation().getLon()))
                .title(e.getLocation().getName())
                .snippet(PrettyDate.getTimeAgo(e.getDate()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        setUpClusterer(mEarthquakeList);

        // CHECK WHAT STATE THE FRAGMENT IS BEING USED IN
        if (!multipleMarkers) {

            plotEarthquake(mMap, mEarthquake);

        } else {

            plotEarthquakes(mMap, mEarthquakeList);

        }




        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.d(TAG, "onInfoWindowClick: " + marker.toString());

                if(mHashMap.get(marker) != null) {
                    int pos = mHashMap.get(marker);

                    Intent i = new Intent(getContext(), EarthquakeDetailActivity.class);
                    i.putExtra(EARTHQUAKE_TRANSFER, mEarthquakeList.get(pos));
                    startActivity(i);

                }



            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Log.d(TAG, "onMarkerClick: ");


                if(mHashMap.get(marker) != null) {
                    int pos = mHashMap.get(marker);
                    Log.d(TAG, "onMarkerClick: " + mEarthquakeList.get(pos).toString());
                }

                float markerZoom = 8.0f;



                // ONLY ZOOM IF FURTHER OUT THAN MAX ZOOM
                if(mMap.getCameraPosition().zoom < 8.0f) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 8f));
                } else {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), mMap.getCameraPosition().zoom));
                }
                marker.showInfoWindow();


                return true;
            }
        });

    }




    private void setUpClusterer(ArrayList<Earthquake> earthquakeList) {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        MarkerManager t = new MarkerManager(mMap);
//        t.newCollection("Test");

        MarkerManager.Collection mMarkers = t.newCollection();

//        for(Earthquake e : earthquakeList){
//        mMarkers.addMarker(createMarker(e));
//        }



        mEarthquakeClusterManager = new ClusterManager<Earthquake>(getContext(), mMap,t);
        mEarthquakeClusterManager.setRenderer(new EarthquakeClusterRenderer(getContext(),mMap, mEarthquakeClusterManager));




        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mEarthquakeClusterManager );
        mMap.setOnMarkerClickListener(mEarthquakeClusterManager );

        mEarthquakeClusterManager.cluster();

    }


    private void addClusterItem(Earthquake e) {

            mEarthquakeClusterManager.addItem(e);

    }


}
