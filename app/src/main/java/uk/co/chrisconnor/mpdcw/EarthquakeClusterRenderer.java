package uk.co.chrisconnor.mpdcw;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeClusterRenderer extends DefaultClusterRenderer<Earthquake> {

    public EarthquakeClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<Earthquake> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Earthquake item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected int getColor(int clusterSize) {
        return Color.rgb(160,28,36);
    }
}
