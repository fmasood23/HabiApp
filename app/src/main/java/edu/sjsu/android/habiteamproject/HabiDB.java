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


    public HabiDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        //execSQL other tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //execSQL drop other tables
        onCreate(sqLiteDatabase);
    }

    public long insertLogin(String username, String password, String eMail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(userName, username);
        contentValues.put(passWord, password);
        contentValues.put(email, eMail);

        SQLiteDatabase database = getWritableDatabase();
        return database.insertOrThrow(TABLE_NAME, null, contentValues);
    }

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
        return database.rawQuery("SELECT password FROM login WHERE username = " + '"' + user + '"' +";", null);
    }
}
