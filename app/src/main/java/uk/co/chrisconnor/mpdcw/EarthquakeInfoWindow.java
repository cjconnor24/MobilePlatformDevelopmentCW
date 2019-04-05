package uk.co.chrisconnor.mpdcw;

import android.app.Activity;
import android.content.Context;
import android.view.View;

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

        return null;
    }
}
