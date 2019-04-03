package uk.co.chrisconnor.mpdcw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.chrisconnor.mpdcw.DAO.CardinalDirection;
import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

import static uk.co.chrisconnor.mpdcw.BaseActivity.EARTHQUAKE_TRANSFER;

/**
 * Dashboard Fragment to provide dashboard view and associated functionality
 */
public class DashboardFragment extends Fragment implements CardView.OnClickListener {


    // DIFFERENT DASHBOARD CARD OPTIONS
    CardView mDashboardStrongest;
    CardView mDashboardDeepest;
    CardView mDashboardNorth;
    CardView mDashboardEast;
    CardView mDashboardWest;
    CardView mDashboardSouth;


    public DashboardFragment() {
        // Required empty public constructor
    }


    /**
     * Create new instance of the fragments
     * @return
     */
    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflate the layout and initialise the card views
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // GET THE VIEWS
        mDashboardStrongest = (CardView) v.findViewById(R.id.dashboardStrongest);
        mDashboardDeepest = (CardView) v.findViewById(R.id.dashboardDeepest);
        mDashboardNorth = (CardView) v.findViewById(R.id.dashboardNorth);
        mDashboardEast = (CardView) v.findViewById(R.id.dashboardEast);
        mDashboardWest = (CardView) v.findViewById(R.id.dashboardWest);
        mDashboardSouth = (CardView) v.findViewById(R.id.dashboardSouth);

        // SET THE ON CLICK LISTENER
        mDashboardStrongest.setOnClickListener(this);
        mDashboardDeepest.setOnClickListener(this);
        mDashboardNorth.setOnClickListener(this);
        mDashboardEast.setOnClickListener(this);
        mDashboardWest.setOnClickListener(this);
        mDashboardSouth.setOnClickListener(this);

        return v;
    }

    /**
     * Handle the click events
     * @param v View which fired the events
     */
    @Override
    public void onClick(View v) {

        Earthquake e;
        Intent i;

        // CHECK WHICH BUTTON WAS PRESSEd
        switch (v.getId()) {

            case R.id.dashboardStrongest:

                e = EarthquakeDatabase.mEarthquakeDao.getStrongestEarthquake();
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);

                break;
            case R.id.dashboardDeepest:
                e = EarthquakeDatabase.mEarthquakeDao.getDeepestEarthquake();
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardNorth:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake(CardinalDirection.NORTH);
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardEast:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake(CardinalDirection.EAST);
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardWest:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake(CardinalDirection.WEST);
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardSouth:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake(CardinalDirection.SOUTH);
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;

        }

    }

}
