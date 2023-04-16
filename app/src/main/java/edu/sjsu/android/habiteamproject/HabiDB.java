package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HabiDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habiDB";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "login";

    private static final String userName = "username";
    private static final String passWord = "password";
    private static final String email = "email";

    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " ("+ userName + " TEXT PRIMARY KEY NOT NULL, "
                    + passWord + " TEXT NOT NULL, "
                    + email + " TEXT NOT NULL);";


    public static final String TABLE_NAME_SLEEP = "sleep";

    private static final String userName_sleep = "username";
    private static final String num_Hours = "num_hours";

    static final String CREATE_TABLE_SLEEP =
            " CREATE TABLE " + TABLE_NAME_SLEEP +
                    " ("+ userName_sleep + " TEXT NOT NULL, "
                    + num_Hours + " DOUBLE);";

    public static final String TABLE_NAME_CURRENT = "current";

    private static final String userName_current = "username";
    private static final String logged_in = "logged_in";

    static final String CREATE_TABLE_CURRENT =
            " CREATE TABLE " + TABLE_NAME_CURRENT +
                    " ("+ userName_current + " TEXT PRIMARY KEY NOT NULL, "
                    + logged_in + " TEXT NOT NULL);";

    public HabiDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SLEEP);
        sqLiteDatabase.execSQL(CREATE_TABLE_CURRENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //execSQL drop other tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SLEEP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CURRENT);
        onCreate(sqLiteDatabase);
    }

//    public long insertLogin(String username, String password, String eMail) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(userName, username);
//        contentValues.put(passWord, password);
//        contentValues.put(email, eMail);
//
//        SQLiteDatabase database = getWritableDatabase();
//        return database.insertOrThrow(TABLE_NAME, null, contentValues);
//    }

    public long insert(String table, ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insertOrThrow(table, null, contentValues);
    }

    public Cursor getAllUsers(String orderBy) {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_NAME,
                new String[]{userName, passWord, email},
                null, null, null, null, orderBy);
    }

    public Cursor getLogin(String user) {
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery("SELECT password FROM login WHERE username = " + '"' + user + '"' + ";", null);
    }

    public Cursor getUser() {
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery("SELECT username FROM current WHERE logged_in = 'true';", null);
    }

    public Cursor getNumSleep(String user) {
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery("SELECT num_hours FROM sleep WHERE username = " + '"' + user + '"' + ";", null);
    }

    public boolean updateLogin(ContentValues contentValues, String user) {
        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_NAME_CURRENT, contentValues, "username = ?", new String[] {user});
        return true;
    }
}
