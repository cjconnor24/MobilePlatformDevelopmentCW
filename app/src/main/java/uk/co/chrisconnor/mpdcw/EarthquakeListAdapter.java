package uk.co.chrisconnor.mpdcw;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
        viewHolder.eDate.setText(PrettyDate.getTimeAgo(currentEarthquake.getDate()));
        viewHolder.eMagnitude.setText(String.valueOf(currentEarthquake.getMagnitude()));

        // SET THE BACKGROUND COLOR OF MAGNITUDE
        Drawable d = viewHolder.eMagnitude.getBackground();
        d.setColorFilter(getColor(currentEarthquake.getMagnitude()+2), PorterDuff.Mode.MULTIPLY);


        return convertView;
    }

    private int getColor(double magnitude){

        int color;
        Double m = Math.abs(magnitude);
        int mag = m.intValue();

        switch(mag){
            case 0:
                color = Color.parseColor("#99FF99");
                break;
            case 1:
                color = Color.parseColor("#BBFF77");
                break;
            case 2:
                color = Color.parseColor("#DDFF55");
                break;
            case 3:
                color = Color.parseColor("#FFFF33");
                break;
            case 4:
                color = Color.parseColor("#FBD33D");
                break;
            case 5:
                color = Color.parseColor("#F8A746");
                break;
            case 6:
                color = Color.parseColor("#F47B50");
                break;
            case 7:
                color = Color.parseColor("#E35F44");
                break;
            case 8:
                color = Color.parseColor("#D24339");
                break;
            case 9:
                color = Color.parseColor("#C1272D");
                break;
            default:
                color = Color.parseColor("#99FF99");
                break;
        }

        return color;

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
