package uk.co.chrisconnor.mpdcw;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

import static uk.co.chrisconnor.mpdcw.BaseActivity.EARTHQUAKE_TRANSFER;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements CardView.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView mDashboardStrongest;
    CardView mDashboardDeepest;
    CardView mDashboardNorth;
    CardView mDashboardEast;
    CardView mDashboardWest;
    CardView mDashboardSouth;

//    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    @Override
    public void onClick(View v) {

        Earthquake e;
        Intent i;

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
                startActivity(i);                break;
            case R.id.dashboardNorth:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake('n');
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardEast:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake('e');
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardWest:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake('w');
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;
            case R.id.dashboardSouth:
                e = EarthquakeDatabase.mEarthquakeDao.getFurtherstCardinalEarthquake('s');
                i = new Intent(getContext(), EarthquakeDetailActivity.class);
                i.putExtra(EARTHQUAKE_TRANSFER, e);
                startActivity(i);
                break;

        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
