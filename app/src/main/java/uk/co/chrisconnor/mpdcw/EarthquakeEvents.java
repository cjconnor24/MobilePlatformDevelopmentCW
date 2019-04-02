package uk.co.chrisconnor.mpdcw;

import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public interface EarthquakeEvents {

    public void earthquakesUpdated(List<Earthquake> earthquakes);

}
