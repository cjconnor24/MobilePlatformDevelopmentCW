package uk.co.chrisconnor.mpdcw.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DbProvider {

    private SQLiteDatabase mDb;

    public DbProvider(SQLiteDatabase db) {
        this.mDb = db;
    }

    public long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }

//    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) {
//        return mDb.update(tableName, values, selection,
//                selectionArgs);
//    }

    public int delete(String tableName, String selection,
                      String[] selectionArgs) {
        return mDb.delete(tableName, selection, selectionArgs);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder) {
        final Cursor cursor = mDb.query(tableName, columns, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String sortOrder, String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, null, null, sortOrder, limit);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDb.rawQuery(sql, selectionArgs);
    }

    /**
     *
     * @param cursor
     * @param <T>
     * @return
     */
    protected abstract <T> T cursorToEntity(Cursor cursor);

}