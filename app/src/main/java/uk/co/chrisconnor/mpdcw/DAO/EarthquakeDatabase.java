package uk.co.chrisconnor.mpdcw.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EarthquakeDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Earthquakes.db";
    private static final int DATABASE_VERSION = 1;



    public EarthquakeDatabase(Context context, String name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public EarthquakeDatabase newInstance(Context context){

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
