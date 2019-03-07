package uk.co.chrisconnor.mpdcw.ui.earthquakedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import uk.co.chrisconnor.mpdcw.PrettyDate;
import uk.co.chrisconnor.mpdcw.R;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeDetailFragment extends Fragment {

    private static final String TAG = "EarthquakeDetailFrag";
    private Earthquake earthquake;

    public static EarthquakeDetailFragment newInstance() {
        return new EarthquakeDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: starts");

        Bundle b = getArguments();
        if (b != null && b.containsKey("earthquake")) {

            earthquake = (Earthquake) b.getSerializable("earthquake");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.earthquake_detail_fragment, container, false);

        if (earthquake != null) {

            Resources resources = getResources();

            TextView title = (TextView) view.findViewById(R.id.location_heading);
            title.setText(earthquake.getLocation().getName());

            TextView subtitle = (TextView) view.findViewById(R.id.datetime_heading);
            subtitle.setText(PrettyDate.getTimeAgo(earthquake.getDate()));

            TextView date = (TextView)view.findViewById(R.id.detail_date);
            date.setText(earthquake.getDate().toString());

            TextView latitude = (TextView)view.findViewById(R.id.detail_lat);
            TextView longitude  = (TextView)view.findViewById(R.id.detail_lon);
            latitude.setText(String.valueOf(earthquake.getLocation().getLat()));
            longitude.setText(String.valueOf(earthquake.getLocation().getLon()));

            TextView magnitute = (TextView)view.findViewById(R.id.detail_magnitude);
            magnitute.setText(String.valueOf(earthquake.getMagnitude()));

            TextView depth = (TextView)view.findViewById(R.id.detail_depth);
            depth.setText(resources.getString(R.string.earthquake_detail_depth, String.valueOf(earthquake.getDepth())));



        }

        return view;
//        return inflater.inflate(R.layout.earthquake_detail_fragment, container, false);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(EarthquakeDetailViewModel.class);
//        // TODO: Use the ViewModel
//    }

}
