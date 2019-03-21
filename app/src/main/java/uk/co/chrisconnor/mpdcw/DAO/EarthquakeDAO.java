package uk.co.chrisconnor.mpdcw.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeDAO implements IEarthquakeDAO {

    SQLiteDatabase db;

    public EarthquakeDAO(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Earthquake getEarthquakeById(int earthquakeId) {
        return null;
    }

    @Override
    public List<Earthquake> fetchAllEarthquakes() {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + )
        return null;
    }

    @Override
    public boolean addEarthquake(Earthquake earthquake) {
        return false;
    }

    @Override
    public boolean addEarthquakes(List<Earthquake> earthquakes) {
        return false;
    }
}
