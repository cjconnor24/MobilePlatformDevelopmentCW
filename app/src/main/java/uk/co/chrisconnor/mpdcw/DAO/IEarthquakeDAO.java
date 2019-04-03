package uk.co.chrisconnor.mpdcw.DAO;

import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * Earthquake DAO Interface
 *
 * @author Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
public interface IEarthquakeDAO {

    public Earthquake getEarthquakeById(int earthquakeId);
    public List<Earthquake> fetchAllEarthquakes();
    public boolean addEarthquake(Earthquake earthquake);
    public boolean addEarthquakes(List<Earthquake> earthquakes);
//    public boolean deleteEarthquake(Earthquake earthquake);
//    public boolean updateEarthquake(int earthquakeId, Earthquake earthquake);

}
