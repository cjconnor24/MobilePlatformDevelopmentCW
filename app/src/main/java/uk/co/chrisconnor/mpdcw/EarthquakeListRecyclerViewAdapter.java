/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.co.chrisconnor.mpdcw.EarthquakeListFragment.OnListFragmentInteractionListener;
import uk.co.chrisconnor.mpdcw.helpers.ColorHelper;
import uk.co.chrisconnor.mpdcw.helpers.PrettyDate;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

import java.util.List;

/**
 * Recycler view adapter to populate and display list of earthquakes
 */
public class EarthquakeListRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeListRecyclerViewAdapter.EarthquakeViewHolder> {

    private static final String TAG = "EarthquakeRecyclerViewA";
    private final List<Earthquake> mEarthquakes;
    private final OnListFragmentInteractionListener mCallback;

    /**
     * Constructor
     * @param items Earthquakes to show
     * @param callback Callback to handle click
     */
    public EarthquakeListRecyclerViewAdapter(List<Earthquake> items, OnListFragmentInteractionListener callback) {
        mEarthquakes = items;
        mCallback = callback;
    }

    /**
     * On create view holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public EarthquakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_earthquake_list_row, parent, false);
        return new EarthquakeViewHolder(view);
    }

    /**
     * Bind the variables to the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final EarthquakeViewHolder holder, int position) {
        holder.mItem = mEarthquakes.get(position);

        // UPDATE THE VALUES
        Earthquake currentEarthquake = mEarthquakes.get(position);
        holder.eLocation.setText(currentEarthquake.getLocation().getName());
        holder.eDate.setText(PrettyDate.getTimeAgo(currentEarthquake.getDate()));
        holder.eMagnitude.setText(String.valueOf(currentEarthquake.getMagnitude()));

        // SET THE BACKGROUND COLOR OF MAGNITUDE
        Drawable d = holder.eMagnitude.getBackground();
        d.setColorFilter(ColorHelper.getColor(currentEarthquake.getMagnitude()), PorterDuff.Mode.MULTIPLY);

        // SET ONCLICK LISTENER
//        holder.eIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: YOU CLICKED THE ICON FOR " + holder.mItem.getLocation().getName());
//            }
//        });


        // SET ONCLICK LISTENER - TELL THE ACTIVITY WHICH EARTHQUAKE WAS CLICKED
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mCallback) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mCallback.onListEarthquakeListItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return ((mEarthquakes != null) && (mEarthquakes.size() != 0) ? mEarthquakes.size() : 0);
    }

    /**
     * View holder to represent the view and used to replace and re-use
     */
    public class EarthquakeViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //        public final TextView mIdView;
//        public final TextView mContentView;
        public Earthquake mItem;

        final TextView eDate;
        final TextView eLocation;
        final TextView eMagnitude;
        final ImageView eIcon;

        /**
         * Returns a view to be reused
         * @param view
         */
        public EarthquakeViewHolder(View view) {
            super(view);
            mView = view;
            this.eDate = view.findViewById(R.id.eDate);
            this.eLocation = view.findViewById(R.id.eLocation);
            this.eMagnitude = view.findViewById(R.id.eMagnitude);
            this.eIcon = view.findViewById(R.id.imageView);
        }

    }
}
