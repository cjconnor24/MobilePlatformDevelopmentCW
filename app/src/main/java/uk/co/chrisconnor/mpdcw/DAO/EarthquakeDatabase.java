package uk.co.chrisconnor.mpdcw.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EarthquakeDatabase {

    private static final String TAG = "EarthquakeDatabase";

    private final Context mContext;
    private DatabaseHelper mDatabaseHelper;
    public static EarthquakeDAO mEarthquakeDao;

    public EarthquakeDatabase (Context context){
        this.mContext = context;
    }

    public EarthquakeDatabase open() {

        mDatabaseHelper = DatabaseHelper.getInstance(mContext);
        SQLiteDatabase mDb = mDatabaseHelper.getWritableDatabase();
        mEarthquakeDao = new EarthquakeDAO(mDb);

        return this;

    }

    public void close() {
        mDatabaseHelper.close();
    }


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

}
