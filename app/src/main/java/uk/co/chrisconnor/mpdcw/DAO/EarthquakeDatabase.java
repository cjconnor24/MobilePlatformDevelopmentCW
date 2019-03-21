package uk.co.chrisconnor.mpdcw.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EarthquakeDatabase extends SQLiteOpenHelper implements IEarthquakeTableSchema {

    private static final String TAG = "EarthquakeDatabase";

    private static EarthquakeDatabase instance = null;

    public static EarthquakeDAO earthquakeDAO;



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
