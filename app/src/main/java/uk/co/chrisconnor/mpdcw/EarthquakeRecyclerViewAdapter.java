package uk.co.chrisconnor.mpdcw;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.chrisconnor.mpdcw.EarthquakeListFragment.OnListFragmentInteractionListener;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Earthquake} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EarthquakeRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.EarthquakeViewHolder> {

    private static final String TAG = "EarthquakeRecyclerViewA";
    private final List<Earthquake> mEarthquakes;
    private final OnListFragmentInteractionListener mCallback;

    public EarthquakeRecyclerViewAdapter(List<Earthquake> items, OnListFragmentInteractionListener callback) {
        mEarthquakes = items;
        mCallback = callback;
    }

    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_earthquake, parent, false);
        return new EarthquakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EarthquakeViewHolder holder, int position) {
        holder.mItem = mEarthquakes.get(position);

        Earthquake currentEarthquake = mEarthquakes.get(position);
        Log.d(TAG, "onBindViewHolder: " + currentEarthquake.toString());

//        holder.mIdView.setText(mEarthquakes.get(position).id);
//        holder.mContentView.setText(mEarthquakes.get(position).content);

        holder.eLocation.setText(currentEarthquake.getLocation().getName());
        holder.eDate.setText(PrettyDate.getTimeAgo(currentEarthquake.getDate()));
        holder.eMagnitude.setText(String.valueOf(currentEarthquake.getMagnitude()));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mCallback) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mCallback.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return ((mEarthquakes != null) && (mEarthquakes.size() != 0) ? mEarthquakes.size() : 0);
    }

    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final TextView mIdView;
//        public final TextView mContentView;
        public Earthquake mItem;

        final TextView eDate;
        final TextView eLocation;
        final TextView eMagnitude;

        public EarthquakeViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.item_number);
//            mContentView = (TextView) view.findViewById(R.id.content);

            this.eDate = view.findViewById(R.id.eDate);
            this.eLocation = view.findViewById(R.id.eLocation);
            this.eMagnitude = view.findViewById(R.id.eMagnitude);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
