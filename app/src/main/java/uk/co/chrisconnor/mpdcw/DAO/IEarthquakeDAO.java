package uk.co.chrisconnor.mpdcw.DAO;

import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public interface IEarthquakeDAO {

    public Earthquake getEarthquakeById(int earthquakeId);
    public List<Earthquake> fetchAllEarthquakes();
    public boolean addEarthquake(Earthquake earthquake);
    public boolean addEarthquakes(List<Earthquake> earthquakes);
//    public boolean deleteEarthquake(Earthquake earthquake);
//    public boolean updateEarthquake(int earthquakeId, Earthquake earthquake);

}
