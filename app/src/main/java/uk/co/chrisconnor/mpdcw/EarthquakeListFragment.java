package uk.co.chrisconnor.mpdcw;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EarthquakeListFragment extends Fragment{

    private static final String TAG = "EarthquakeListFragment";
    private static final String EARTHQUAKES = "earthquakes";
    // TODO: Customize parameters
    private ArrayList<Earthquake> mEarthquakes;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EarthquakeListFragment() {
    }

    public static EarthquakeListFragment newInstance(ArrayList<Earthquake> earthquakes) {

        EarthquakeListFragment fragment = new EarthquakeListFragment();
        Bundle args = new Bundle();
        args.putSerializable(EARTHQUAKES, earthquakes);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: CREATE LIST FRAGMENT");

        if (getArguments() != null) {
            mEarthquakes = (ArrayList<Earthquake>) getArguments().getSerializable(EARTHQUAKES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new EarthquakeListRecyclerViewAdapter(mEarthquakes, mListener));

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

//    BELOW IS EXPERIMENTING WITH FRAGMENT LIFECYCLE

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        Log.d(TAG, "onDetach: This was detached");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "LIST FRAGMENT: This was destroyed");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "LIST FRAGMENT: This was paused");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "LIST FRAGMENT: This was resumed");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListEarthquakeListItemClick(Earthquake item);
    }
}
