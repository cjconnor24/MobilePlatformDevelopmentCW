package uk.co.chrisconnor.mpdcw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthquakeListAdapter extends ArrayAdapter {

    private static final String TAG = "EarthquakeListAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Earthquake> earthquakes;

    public EarthquakeListAdapter(Context context, int resource, List<Earthquake> earthquakes){
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = earthquakes;
    }

    @Override
    public int getCount() {
        return earthquakes.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // THIS LETS US REUSE VIEWS SO IT DOES CHEW UP MEMORY
        ViewHolder viewHolder;

        // CHECK TO SEE IF THERE IS A VIEW TO RE-USE
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Earthquake currentEarthquake = earthquakes.get(position);

        viewHolder.eLocation.setText(currentEarthquake.getLocation().getName());
        viewHolder.eDate.setText(currentEarthquake.getDate().toString());
        viewHolder.eMagnitude.setText(String.valueOf(currentEarthquake.getMagnitude()));

        return convertView;
    }

    // THIS CREATES A RE-USABLE VIEW
    private class ViewHolder {

        final TextView eDate;
        final TextView eLocation;
        final TextView eMagnitude;

        ViewHolder(View v) {
            this.eDate = v.findViewById(R.id.eDate);
            this.eLocation = v.findViewById(R.id.eLocation);
            this.eMagnitude = v.findViewById(R.id.eMagnitude);
        }

    }

}
