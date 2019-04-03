/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import uk.co.chrisconnor.mpdcw.models.Earthquake;
import uk.co.chrisconnor.mpdcw.models.Location;

public class EarthquakeDAO extends DbProvider implements IEarthquakeTableSchema, IEarthquakeDAO {

    SQLiteDatabase db;
    private static final String TAG = "EarthquakeDAO";

    public EarthquakeDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * Convert cursor data to Earthquake Entity
     * @param cursor Curson containing Earthquake Data
     * @return Earthquake Entity
     */
    @Override
    protected Earthquake cursorToEntity(Cursor cursor) {

        // CREATE NEW EARTHQUAKE AND GET COLUMN INDEXES
        Earthquake earthquake = new Earthquake();
        int id, location, magnitude, depth, lat, lon, link, datetime;

        // IF NOT BLANK, GET THE INDEXES
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

            // CREATE NEW LOCATION TO BUILD OBJECT
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

            // ADD THE LOCATION TO THE EARTHQUAKE
            earthquake.setLocation(l);

        }
        return earthquake;
    }

    /**
     * Return earthquake based on the ID
     * @param earthquakeId Earthquake of ID to retrieve
     * @return Earthquake
     */
    @Override
    public Earthquake getEarthquakeById(int earthquakeId) {

        String selectionArgs[] = {String.valueOf(earthquakeId)};
        String selection = COLUMN_NAME_ID + " = ?";
        Cursor cursor = super.query(TABLE_NAME, EARTHQUAKE_COLUMNS, selection, selectionArgs, COLUMN_NAME_ID);
        Earthquake e = null;
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                e = cursorToEntity(cursor);
            } while (cursor.moveToNext());
        }
        return e;
    }

    /**
     * Returns the strongest earthquake from the database
     * @return Earthquake strongest
     */
    public Earthquake getStrongestEarthquake() {
        return runSingleRawQuery("SELECT * FROM " + IEarthquakeTableSchema.TABLE_NAME + " ORDER BY " + COLUMN_NAME_MAGNITUDE + " DESC LIMIT 0,1");
    }

    /**
     * Returns the deepest earthquake from the database
     * @return Earthquake deepest
     */
    public Earthquake getDeepestEarthquake() {
        return runSingleRawQuery("SELECT * FROM " + IEarthquakeTableSchema.TABLE_NAME + " ORDER BY " + COLUMN_NAME_DEPTH + " DESC LIMIT 0,1");
    }

    /**
     * Get the most extremely positioned Earthquake based on the supplied cardinality i.e. N, S, E, W
     * @param d Direction from which to get most extreme earthquake
     * @return
     */
    public Earthquake getFurtherstCardinalEarthquake(CardinalDirection d) {

        String field="";
        String sortDirection ="";

        switch(d){
            case NORTH:
                field = COLUMN_NAME_LAT;
                sortDirection = "DESC";
                break;
            case SOUTH:
                field = COLUMN_NAME_LAT;
                sortDirection = "ASC";
                break;
            case EAST:
                field = COLUMN_NAME_LONG;
                sortDirection = "DESC";
                break;
            case WEST:
                field = COLUMN_NAME_LONG;
                sortDirection = "ASC";
                break;
        }

        return runSingleRawQuery("SELECT * FROM " + IEarthquakeTableSchema.TABLE_NAME + " ORDER BY " + field + " "+sortDirection+" LIMIT 0,1");
    }

    /**
     * Search earthquake based on the supplied arguments
     * @param pStartDate Start date to search
     * @param pEndDate End date to search
     * @param pMagnitude Magnitude to search
     * @param pDepth Depth to search
     * @param pLocation Location string to search
     * @param pSort Order in which to sort them
     * @param pSortBy Order direction in which to sort them
     * @return List of earthquakes mathching search
     */
    public List<Earthquake> searchEarthquake(Date pStartDate, Date pEndDate, Integer pMagnitude, Integer pDepth, String pLocation, Integer pSort, String pSortBy) {

        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<String> conditions = new ArrayList<>();

        if (pStartDate != null) {
            conditions.add(String.format("%s >= %s", IEarthquakeTableSchema.COLUMN_NAME_DATETIME, pStartDate.getTime()));
        }

        if (pEndDate != null) {
            conditions.add(String.format("%s <= %s", IEarthquakeTableSchema.COLUMN_NAME_DATETIME, pEndDate.getTime()));
        }

        if (pMagnitude != null) {
            conditions.add(String.format("%s <= %s", IEarthquakeTableSchema.COLUMN_NAME_MAGNITUDE, pMagnitude));
        }

        if (pDepth != null) {
            conditions.add(String.format("%s >= %s", IEarthquakeTableSchema.COLUMN_NAME_DEPTH, pDepth));
        }

        if (pLocation != null && !pLocation.equals("")) {
            conditions.add(String.format("%s LIKE '%%%s%%'", IEarthquakeTableSchema.COLUMN_NAME_LOCATION, pLocation));
        }


        if (conditions.size() > 0) {
            query += " WHERE " + TextUtils.join(" AND ", conditions);
        }

        String sortOrder = " ORDER BY ";
        Log.d(TAG, "searchEarthquake: PSORT IN" + pSort);
        if (pSort != null) {

            switch (pSort) {
                case 0:
                    sortOrder += IEarthquakeTableSchema.COLUMN_NAME_DATETIME;
                    break;
                case 1:
                    sortOrder += IEarthquakeTableSchema.COLUMN_NAME_DEPTH;
                    break;
                case 2:
                    sortOrder += IEarthquakeTableSchema.COLUMN_NAME_LOCATION;
                    break;
                case 3:
                    sortOrder += IEarthquakeTableSchema.COLUMN_NAME_MAGNITUDE;
                    break;
                default:
//                    sortOrder += IEarthquakeTableSchema.COLUMN_NAME_DATETIME;
                    break;
            }
        }

        // GET SORT DIRECTION
        query += sortOrder + " " + pSortBy + ";";

        List<Earthquake> earthquakes = new ArrayList<>();

        Log.d(TAG, query);

        Cursor cursor = super.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                earthquakes.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return earthquakes;

    }

    /**
     * Run a raw query on the database with a single result
     * @param query
     * @return
     */
    private Earthquake runSingleRawQuery(String query) {

        Earthquake e = null;

        Cursor cursor = super.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            e = cursorToEntity(cursor);
            cursor.close();
        }

        return e;
    }

    /**
     * Fetch all Earthquakes from the data base.
     * @return
     */
    @Override
    public List<Earthquake> fetchAllEarthquakes() {
        List<Earthquake> earthquakes = new ArrayList<Earthquake>();
        Cursor cursor = super.query(TABLE_NAME, EARTHQUAKE_COLUMNS, null,
                null, COLUMN_NAME_ID+" DESC");

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

    /**
     * Add a single earthquake to the database
     * @param earthquake Earthquake to add
     * @return true or false if successful
     */
    @Override
    public boolean addEarthquake(Earthquake earthquake) {

        try {
            return super.insert(TABLE_NAME, getContentValues(earthquake)) > 0;
        } catch (SQLiteConstraintException ex) {

            // CATCH THE Contstraint Exception if the EQ already exists in the Database
            return false;
        }
    }


    /**
     * Add a list of earthquakes
     * @param earthquakes Earthquakes to add
     * @return Return true or false depending on result
     */
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

    /**
     * Get the Earthquake properties as content values
     * @param e Earthquake to extract details
     * @return Content values for the Earthquake
     */
    private ContentValues getContentValues(Earthquake e) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_ID, e.getId());
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
