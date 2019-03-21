package uk.co.chrisconnor.mpdcw.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EarthquakeDatabase extends SQLiteOpenHelper {

    private static final String TAG = "EarthquakeDatabase";


    // DB AND TABLE NAME INFO
    private static final String TABLE_NAME = "Earthquakes";
    private static final String DATABASE_NAME = "Earthquakes.db";
    private static final int DATABASE_VERSION = 1;

    // COLUMN NAMES
    private static final String COLUMN_NAME_ID = "_id";
    private static final String COLUMN_NAME_LOCATION = "location";
    private static final String COLUMN_NAME_MAGNITUDE = "magnitude";
    private static final String COLUMN_NAME_DEPTH = "depth";
    private static final String COLUMN_NAME_LAT = "lat";
    private static final String COLUMN_NAME_LONG = "long";
    private static final String COLUMN_NAME_LINK = "link";
    private static final String COLUMN_NAME_DATETIME = "datetime";

    private static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ( "
            + COLUMN_NAME_ID
            + " TEXT NOT NULL UNIQUE, "
            + COLUMN_NAME_LOCATION
            + " TEXT, "
            + COLUMN_NAME_MAGNITUDE
            + " NUMERIC, "
            + COLUMN_NAME_DEPTH
            + " INTEGER NOT NULL, "
            + COLUMN_NAME_LAT
            + " NUMERIC, "
            + COLUMN_NAME_LONG
            + " NUMERIC, "
            + COLUMN_NAME_LINK
            + " TEXT, "
            + COLUMN_NAME_DATETIME
            + " NUMERIC, PRIMARY KEY("+COLUMN_NAME_ID+") );";

    private static EarthquakeDatabase instance = null;



    private EarthquakeDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "EarthquakeDatabase: CONSTRUCTOR");
    }

    /**
     * Getting a singleton of the DB to avoid any access / writing clashes
     *
     * @param context
     * @return
     */
    public static EarthquakeDatabase getInstance(Context context) {

        Log.d(TAG, "getInstance: ");

        if (instance == null) {
            instance = new EarthquakeDatabase(context);
        }

        return instance;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate: " + USER_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG, "onUpgrade: would upgrade the database");

    }
}
