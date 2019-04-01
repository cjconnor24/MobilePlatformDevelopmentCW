package uk.co.chrisconnor.mpdcw.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import uk.co.chrisconnor.mpdcw.models.Earthquake;
import uk.co.chrisconnor.mpdcw.models.Location;

public class EarthquakeDAO extends DbProvider implements IEarthquakeTableSchema, IEarthquakeDAO {

    SQLiteDatabase db;

    public EarthquakeDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Earthquake cursorToEntity(Cursor cursor) {

        Earthquake earthquake = new Earthquake();


        int id;
        int location;
        int magnitude;
        int depth;
        int lat;
        int lon;
        int link;
        int datetime;

//        String COLUMN_NAME_ID = "_id";
//        String COLUMN_NAME_LOCATION = "location";
//        String COLUMN_NAME_MAGNITUDE = "magnitude";
//        String COLUMN_NAME_DEPTH = "depth";
//        String COLUMN_NAME_LAT = "lat";
//        String COLUMN_NAME_LONG = "long";
//        String COLUMN_NAME_LINK = "link";
//        String COLUMN_NAME_DATETIME = "datetime";

        if (cursor != null) {

            if (cursor.getColumnIndex(COLUMN_NAME_ID) != -1) {
                id = cursor.getColumnIndexOrThrow(COLUMN_NAME_ID);
                earthquake.setId(cursor.getString(id));
            }

            if (cursor.getColumnIndex(COLUMN_NAME_MAGNITUDE) != -1) {
                magnitude = cursor.getColumnIndexOrThrow(COLUMN_NAME_MAGNITUDE);
                earthquake.setMagnitude(cursor.getDouble(magnitude));
            }

            if (cursor.getColumnIndex(COLUMN_NAME_DEPTH) != -1) {
                depth = cursor.getColumnIndexOrThrow(COLUMN_NAME_DEPTH);
                earthquake.setDepth(cursor.getInt(depth));
            }

            if (cursor.getColumnIndex(COLUMN_NAME_LINK) != -1) {
                link = cursor.getColumnIndexOrThrow(COLUMN_NAME_LINK);
                earthquake.setLink(cursor.getString(link));
            }

            if (cursor.getColumnIndex(COLUMN_NAME_DATETIME) != -1) {
                datetime = cursor.getColumnIndexOrThrow(COLUMN_NAME_DATETIME);
                earthquake.setDate(new Date(cursor.getLong(datetime)));
            }

//            earthquake.setLocation(new Location());
            Location l = new Location();

            if (cursor.getColumnIndex(COLUMN_NAME_LOCATION) != -1) {
                location = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME_LOCATION);
                l.setName(cursor.getString(location));
            }

            if (cursor.getColumnIndex(COLUMN_NAME_LAT) != -1) {
                lat = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME_LAT);
                l.setLat(cursor.getDouble(lat));
            }

            if (cursor.getColumnIndex(COLUMN_NAME_LONG) != -1) {
                lon = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME_LONG);
                l.setLon(cursor.getDouble(lon));
            }

            earthquake.setLocation(l);


        }
        return earthquake;
    }

    @Override
    public Earthquake getEarthquakeById(int earthquakeId) {
        return null;
    }

    @Override
    public List<Earthquake> fetchAllEarthquakes() {
        List<Earthquake> earthquakes = new ArrayList<Earthquake>();
        Cursor cursor = super.query(TABLE_NAME, EARTHQUAKE_COLUMNS, null,
                null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Earthquake earthquake = cursorToEntity(cursor);
                earthquakes.add(earthquake);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return earthquakes;
    }

    @Override
    public boolean addEarthquake(Earthquake earthquake) {

        try {
            return super.insert(TABLE_NAME, getContentValues(earthquake)) > 0;
        } catch (SQLiteConstraintException ex) {

            // CATCH THE Contstraint Exception if the EQ already exists in the Database
//            Log.w("Database", ex.getMessage());
            return false;
        }
    }


    @Override
    public boolean addEarthquakes(List<Earthquake> earthquakes) {

        boolean result = true;
        for (Earthquake e : earthquakes) {

            if (!addEarthquake(e)) {
                result = false;
            }

        }
        return result;
    }

    private ContentValues getContentValues(Earthquake e) {

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
