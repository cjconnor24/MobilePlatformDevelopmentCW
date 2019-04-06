package uk.co.chrisconnor.mpdcw;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class EarthquakeInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Context mContext;

    public EarthquakeInfoWindow(Context context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View v = ((Activity)mContext).getLayoutInflater().inflate(R.layout.map_info_window, null);

        TextView lblDatetime = v.findViewById(R.id.lblDateTime);
        TextView lblLocation = v.findViewById(R.id.lblLocation);
        TextView lblMagnitude = v.findViewById(R.id.lblMagnitude);
        TextView lblDepth = v.findViewById(R.id.lblDepth);

        return null;
    }
}
