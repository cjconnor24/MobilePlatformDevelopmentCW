package uk.co.chrisconnor.mpdcw;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.chrisconnor.mpdcw.helpers.ColorHelper;
import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XEarthquakeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link XEarthquakeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XEarthquakeDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EARTHQUAKE = "earthquake";


    // TODO: Rename and change types of parameters
    private Earthquake mEarthquake;

    private OnFragmentInteractionListener mListener;

    public XEarthquakeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param earthquake Parameter 1.
     * @return A new instance of fragment XEarthquakeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static XEarthquakeDetailFragment newInstance(Earthquake earthquake) {
        XEarthquakeDetailFragment fragment = new XEarthquakeDetailFragment();
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

        View view = inflater.inflate(R.layout.earthquake_detail_fragment, container, false);

        if (mEarthquake != null) {

            Resources resources = getResources();

            TextView title = (TextView) view.findViewById(R.id.location_heading);
            title.setText(mEarthquake.getLocation().getName());

            TextView subtitle = (TextView) view.findViewById(R.id.datetime_heading);
            subtitle.setText(PrettyDate.getTimeAgo(mEarthquake.getDate()));

            TextView magnitudeHeading = (TextView) view.findViewById(R.id.magnitude_heading);
            magnitudeHeading.setText(String.valueOf(mEarthquake.getMagnitude()));
            Drawable d = magnitudeHeading.getBackground();
            d.setColorFilter(ColorHelper.getColor(mEarthquake.getMagnitude()), PorterDuff.Mode.MULTIPLY);

            TextView date = (TextView) view.findViewById(R.id.detail_date);
            date.setText(mEarthquake.getDate().toString());

            TextView latitude = (TextView) view.findViewById(R.id.detail_lat);
            TextView longitude = (TextView) view.findViewById(R.id.detail_lon);
            latitude.setText(String.valueOf(mEarthquake.getLocation().getLat()));
            longitude.setText(String.valueOf(mEarthquake.getLocation().getLon()));

            TextView magnitute = (TextView) view.findViewById(R.id.detail_magnitude);
            magnitute.setText(String.valueOf(mEarthquake.getMagnitude()));

            TextView depth = (TextView) view.findViewById(R.id.detail_depth);
            depth.setText(resources.getString(R.string.earthquake_detail_depth, String.valueOf(mEarthquake.getDepth())));

        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnDashboardFragmentInteractionListener) {
//            mListener = (OnDashboardFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnDashboardFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
