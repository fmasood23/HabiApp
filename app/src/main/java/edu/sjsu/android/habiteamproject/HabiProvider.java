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

    public static final Uri CONTENT_URI_CURRENT = Uri.parse("content://" + AUTHORITY + "/current");

    public static final Uri CONTENT_URI_CALENDAR = Uri.parse("content://" + AUTHORITY + "/calendar");

    public static final Uri CONTENT_URI_WATER = Uri.parse("content://" + AUTHORITY + "/water");

    public static final Uri CONTENT_URI_TO_DO = Uri.parse("content://" + AUTHORITY + "/todo");

    public static final Uri CONTENT_URI_GROCERY = Uri.parse("content://" + AUTHORITY + "/grocery");

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "login", 100);
        uriMatcher.addURI(AUTHORITY, "sleep", 200);
        uriMatcher.addURI(AUTHORITY, "current", 300);
        uriMatcher.addURI(AUTHORITY, "calendar", 400);
        uriMatcher.addURI(AUTHORITY, "water", 500);
        uriMatcher.addURI(AUTHORITY, "todo", 600);
        uriMatcher.addURI(AUTHORITY, "grocery", 700);
    }

    public HabiProvider() {
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
            case 300:
                long rowID3 = db.insert(HabiDB.TABLE_NAME_CURRENT, values);
                //If record is added successfully
                if (rowID3 > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID3);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            case 400:
                long rowID4 = db.insert(HabiDB.TABLE_NAME_CALENDAR, values);
                //If record is added successfully
                if (rowID4 > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID4);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            case 500:
                long rowID5 = db.insert(HabiDB.TABLE_NAME_WATER, values);
                //If record is added successfully
                if (rowID5 > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID5);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
            case 600:
                long rowID6 = db.insert(HabiDB.TABLE_NAME_TO_DO, values);
                //If record is added successfully
                if (rowID6 > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID6);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }

            case 700:
                long rowID7 = db.insert(HabiDB.TABLE_NAME_GROCERY, values);
                //If record is added successfully
                if (rowID7 > 0) {
                    _uri = ContentUris.withAppendedId(uri, rowID7);
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
            case 200:
                return db.getNumSleep(selection);
            case 300:
                return db.getUser();
            case 400:
                return db.getAllDates(selection);
            case 500:
                return db.getAllWater(selection);
            case 600:
                return db.getAllItems();
            case 700:
                return db.getAllGroceries();
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case 100:
                if (db.updateAcc(values, selection)) {
                    return 1;
                }
            case 300:
                if (selectionArgs!= null && selectionArgs[0].equals("reset")) {
                    if(db.defaultFalse()){
                        return 1;
                    }
                } else {
                    if (db.updateLogin(values, selection)) {
                        return 1;
                    }
                }
        }
        return -1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case 200:
                int deleted2 = db.delete_sleep(selection);
                if (deleted2 >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return deleted2;
                }
            case 400:
                int deleted3 = db.delete_calendar(selection);
                if (deleted3 >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return deleted3;
                }
            case 500:
                int deleted4 = db.delete_water(selection);
                if (deleted4 >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return deleted4;
                }
            case 600:
                int deleted = db.delete_to_do(selection);
                if (deleted >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return deleted;
                }

            case 700:
                int deleted1 = db.delete_grocery(selection);
                if (deleted1 >= 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return deleted1;
                }

        }
        return -1;
    }
}