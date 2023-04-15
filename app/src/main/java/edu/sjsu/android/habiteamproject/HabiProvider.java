package edu.sjsu.android.habiteamproject;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

public class HabiProvider extends ContentProvider {

    private HabiDB db;
    public static final String AUTHORITY = "edu.sjsu.android.habiteamproject";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/login");

    public static final Uri CONTENT_URI_SLEEP = Uri.parse("content://" + AUTHORITY + "/sleep");


    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "login", 100);
        uriMatcher.addURI(AUTHORITY, "sleep", 200);
    }

    public HabiProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;
        switch (uriMatcher.match(uri)) {
            case 100:
                long rowID = db.insert(HabiDB.TABLE_NAME, values);
                //If record is added successfully
                if (rowID > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            case 200:
                long rowID2 = db.insert(HabiDB.TABLE_NAME_SLEEP, values);
                //If record is added successfully
                if (rowID2 > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                //throw new SQLException("Failed to add a record into " + uri);
        }
        return _uri;
    }

    @Override
    public boolean onCreate() {
        db = new HabiDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case 100:
                return db.getLogin(selection);
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}