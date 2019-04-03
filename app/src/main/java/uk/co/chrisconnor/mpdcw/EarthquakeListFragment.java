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

    /**
     * Create new instance of fragment
     * @param earthquakes Earthquakes to list
     * @return
     */
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

        // GET EARTHQUAKES PASSED THROUGH
        if (getArguments() != null) {
            mEarthquakes = (ArrayList<Earthquake>) getArguments().getSerializable(EARTHQUAKES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_earthquake_list, container, false);

        // CREATE NEW RECYCLER VIEW AND INITIALISE WITH THE RECYCLER ADAPTER
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * Interface to allow fragment to generate events which can be captured in the activity above
     */
    public interface OnListFragmentInteractionListener {
        void onListEarthquakeListItemClick(Earthquake item);
    }
}
