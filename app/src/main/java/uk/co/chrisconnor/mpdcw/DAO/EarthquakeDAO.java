package uk.co.chrisconnor.mpdcw.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import uk.co.chrisconnor.mpdcw.models.Earthquake;

public class EarthquakeDAO extends DbProvider implements IEarthquakeTableSchema, IEarthquakeDAO {

    SQLiteDatabase db;

    public EarthquakeDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected <T> T cursorToEntity(Cursor cursor) {
        return null;
    }

    @Override
    public Earthquake getEarthquakeById(int earthquakeId) {
        return null;
    }

    @Override
    public List<Earthquake> fetchAllEarthquakes() {
        return null;
    }

    @Override
    public boolean addEarthquake(Earthquake earthquake) {

        try {
            return super.insert(TABLE_NAME, getContentValues(earthquake)) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }


    @Override
    public boolean addEarthquakes(List<Earthquake> earthquakes) {

        boolean result = true;
        for(Earthquake e : earthquakes){

            if(!addEarthquake(e)){
                result = false;
            }

        }
        return result;
    }

    private ContentValues getContentValues(Earthquake e){

        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_NAME_ID, UUID.randomUUID().toString());
        cv.put(COLUMN_NAME_ID, e.hashCode());
        cv.put(COLUMN_NAME_LOCATION, e.getLocation().getName());
        cv.put(COLUMN_NAME_MAGNITUDE, e.getMagnitude());
        cv.put(COLUMN_NAME_DEPTH, e.getDepth());
        cv.put(COLUMN_NAME_LAT, e.getLocation().getLat());
        cv.put(COLUMN_NAME_LONG, e.getLocation().getLon());
        cv.put(COLUMN_NAME_LINK, e.getLink());
        cv.put(COLUMN_NAME_DATETIME, e.getDate().getTime());

        return cv;

    }

}
