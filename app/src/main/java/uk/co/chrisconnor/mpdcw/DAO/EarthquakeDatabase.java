package uk.co.chrisconnor.mpdcw.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Earthquake database class to separate concerns from the DAO and the Db Provider.
 * This class takes care of creating the data and table if neccessary. It will also get a readable/writeable instance of the DB for use.
 */
public class EarthquakeDatabase {

    private static final String TAG = "EarthquakeDatabase";

    private final Context mContext;
    private DatabaseHelper mDatabaseHelper;
    public static EarthquakeDAO mEarthquakeDao;

    /**
     * Constructor
     * @param context
     */
    public EarthquakeDatabase (Context context){
        this.mContext = context;
    }

    /**
     * Open the database and return instance
     * @return instance of EarthquakeDatabase
     */
    public EarthquakeDatabase open() {

        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
        SQLiteDatabase mDb = mDatabaseHelper.getWritableDatabase();
        mEarthquakeDao = new EarthquakeDAO(mDb);

        return this;

    }

    /**
     * Close the database
     */
    public void close() {
        mDatabaseHelper.close();
    }


    /**
     * Inner class to help with the management of the database itself
     */
    private static class DatabaseHelper extends SQLiteOpenHelper implements IEarthquakeTableSchema {

        private static final String TAG = "EarthquakeDatabase";
        private static DatabaseHelper instance = null;


        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.d(TAG, "EarthquakeDatabase: CONSTRUCTOR");
        }

        /**
         * Getting a singleton of the DB to avoid any access / writing clashes
         *
         * @param context
         * @return
         */
        public static DatabaseHelper getInstance(Context context) {

            Log.d(TAG, "getInstance: ");

            if (instance == null) {
                instance = new DatabaseHelper(context);
            }

            return instance;

        }

        /**
         * Create the database table
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            Log.d(TAG, "onCreate: " + USER_TABLE_CREATE);
            db.execSQL(USER_TABLE_CREATE);

        }

        /**
         * Not implemented but would handle the database upgrade if app was more complex and changed structure etc.
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d(TAG, "onUpgrade: would upgrade the database");

        }
    }

}
