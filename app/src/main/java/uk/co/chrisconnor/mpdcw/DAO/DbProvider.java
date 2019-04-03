package uk.co.chrisconnor.mpdcw.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * DB Provider class to abstract DB operations from the Earthquake DAO
 */
public abstract class DbProvider {

    private SQLiteDatabase mDb;

    public DbProvider(SQLiteDatabase db) {
        this.mDb = db;
    }

    /**
     * Insert operation
     * @param tableName Name of the table
     * @param values Content values to enter
     * @return
     */
    public long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }


    /**
     * Call the SQLiteDatabase query method
     * @param tableName Name of table
     * @param columns Columns to return
     * @param selection
     * @param selectionArgs String aguments
     * @param sortOrder Returned sort order of rows
     * @return Cursor containing results
     */
    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder) {
        final Cursor cursor = mDb.query(tableName, columns, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    /**
     * Call the raw Query Method of the SQLiteDatabse
     * @param sql Query to run
     * @param selectionArgs Columns to retrive
     * @return Cursor containing returned data
     */
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDb.rawQuery(sql, selectionArgs);
    }

    /**
     * Interface to implement cursorToEntity method for converting to object
     * @param cursor
     * @param <T> Type
     * @return Entity of Type<T>
     */
    protected abstract <T> T cursorToEntity(Cursor cursor);

}