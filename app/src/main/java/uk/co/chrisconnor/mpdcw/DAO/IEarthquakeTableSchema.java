package uk.co.chrisconnor.mpdcw.DAO;

public interface IEarthquakeTableSchema {

    String TABLE_NAME = "Earthquakes";
    String DATABASE_NAME = "Earthquakes.db";
    int DATABASE_VERSION = 1;

    //    public COLUMN NAMES
    String COLUMN_NAME_ID = "_id";
    String COLUMN_NAME_LOCATION = "location";
    String COLUMN_NAME_MAGNITUDE = "magnitude";
    String COLUMN_NAME_DEPTH = "depth";
    String COLUMN_NAME_LAT = "lat";
    String COLUMN_NAME_LONG = "long";
    String COLUMN_NAME_LINK = "link";
    String COLUMN_NAME_DATETIME = "datetime";

    String[] EARTHQUAKE_COLUMNS = new String[] { COLUMN_NAME_ID, COLUMN_NAME_LOCATION, COLUMN_NAME_MAGNITUDE, COLUMN_NAME_DEPTH, COLUMN_NAME_LAT, COLUMN_NAME_LONG, COLUMN_NAME_LINK, COLUMN_NAME_DATETIME};

    String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
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
            + " NUMERIC, PRIMARY KEY(" + COLUMN_NAME_ID + ") );";

}
