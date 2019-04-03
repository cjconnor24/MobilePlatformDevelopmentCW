package uk.co.chrisconnor.mpdcw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import uk.co.chrisconnor.mpdcw.helpers.ColorHelper;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * Custom Cluster rendered to customize the look and feel of marker clusters on the map
 */
public class EarthquakeClusterRenderer extends DefaultClusterRenderer<Earthquake> {

    private static final String TAG = "EarthquakeClusterRender";

    private final IconGenerator mClusterIconGenerator;
    private final Context mContext;

    public EarthquakeClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<Earthquake> clusterManager) {

        super(context, map, clusterManager);
        mContext = context;
        mClusterIconGenerator = new IconGenerator(context);


    }

    /**
     * Before cluster gets rendered, carry out these tasks including updating the markers
     * @param item
     * @param markerOptions
     */
    @Override
    protected void onBeforeClusterItemRendered(Earthquake item, MarkerOptions markerOptions) {

        // UPDATE THE MARKER COLOUR BASED ON MAGNITUDE
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
        markerOptions.icon(BitmapDescriptorFactory
                .defaultMarker(ColorHelper.getHue(item.getMagnitude())));

        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }

    /**
     * Get the HUE value based on HEX colour
     * TODO: doesnt really work - need to tweak
     * @param color
     * @return
     */
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    /**
     * Return RED for cluster
     * @param clusterSize
     * @return
     */
    @Override
    protected int getColor(int clusterSize) {
        return Color.rgb(160, 28, 36);
    }
}
