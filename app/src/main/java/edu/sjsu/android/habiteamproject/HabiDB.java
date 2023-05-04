package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;

import androidx.annotation.Nullable;

import java.util.Calendar;

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

    public static final String TABLE_NAME_CALENDAR = "calendar";

    private static final String userName_calendar = "username";
    private static final String date = "date";
    private static final String title = "title";

    static final String CREATE_TABLE_CALENDAR =
            " CREATE TABLE " + TABLE_NAME_CALENDAR +
                    " ("+ userName_calendar + " TEXT NOT NULL, "
                    + date + " TEXT NOT NULL, "
                    + title + " TEXT NOT NULL);";


    public static final String TABLE_NAME_WATER = "water";

    private static final String userName_water = "username";
    private static final String date_water = "date";
    private static final String count = "count";

    static final String CREATE_TABLE_WATER =
            " CREATE TABLE " + TABLE_NAME_WATER +
                    " ("+ userName_water + " TEXT NOT NULL, "
                    + date_water + " TEXT NOT NULL, "
                    + count + " INTEGER);";

    public static final String TABLE_NAME_TO_DO = "todo";

    private static final String userName_to_do = "username";
    private static final String content = "content";

    static final String CREATE_TABLE_TO_DO =
            " CREATE TABLE " + TABLE_NAME_TO_DO +
                    " ("+ userName_to_do + " TEXT NOT NULL, "
                    + content + " TEXT NOT NULL);";

    public static final String TABLE_NAME_GROCERY = "grocery";

    private static final String userName_grocery = "username";
    private static final String grocery_item = "item";

    static final String CREATE_TABLE_GROCERY =
            " CREATE TABLE " + TABLE_NAME_GROCERY +
                    " ("+ userName_grocery + " TEXT NOT NULL, "
                    + grocery_item + " TEXT NOT NULL);";

    public HabiDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SLEEP);
        sqLiteDatabase.execSQL(CREATE_TABLE_CURRENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CALENDAR);
        sqLiteDatabase.execSQL(CREATE_TABLE_WATER);
        sqLiteDatabase.execSQL(CREATE_TABLE_TO_DO);
        sqLiteDatabase.execSQL(CREATE_TABLE_GROCERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SLEEP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CURRENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CALENDAR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WATER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TO_DO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GROCERY);
        onCreate(sqLiteDatabase);
    }

    public long insert(String table, ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insertOrThrow(table, null, contentValues);
    }

    public Cursor getAllDates(String user) {
        SQLiteDatabase database = getWritableDatabase();
        String where = "username = ?";
        String[] whereArgs = new String[] {user};
        return database.query(TABLE_NAME_CALENDAR,
                new String[]{userName_calendar, date, title},
                where, whereArgs, null, null, null);
    }

    public Cursor getAllItems() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_NAME_TO_DO,
                new String[]{userName_to_do, content},
                null, null, null, null, null);
    }

    public Cursor getAllGroceries() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_NAME_GROCERY,
                new String[]{userName_to_do, grocery_item},
                null, null, null, null, null);
    }
    public Cursor getAllWater(String user) {
        SQLiteDatabase database = getWritableDatabase();
        String currentTime = Calendar.getInstance().getTime().toString();
        String[] current = currentTime.split(" ");
        String newCurrent = current[0] + " " + current[1] + " " + current[2];

        String where = "username = ? AND date = ?";
        String[] whereArgs = new String[] {user, newCurrent};

        return database.query(TABLE_NAME_WATER,
                new String[]{count},
                where, whereArgs, null, null, null);
    }

    public Cursor getLogin(String user) {
        SQLiteDatabase database = getWritableDatabase();
        String where = "username = ?";
        String[] whereArgs = new String[] {user};
        return database.query(TABLE_NAME,
                new String[]{passWord},
                where, whereArgs, null, null, null);
    }

    public Cursor getUser() {
        SQLiteDatabase database = getWritableDatabase();
        String where = "logged_in = ?";
        String[] whereArgs = new String[] {"true"};
        return database.query(TABLE_NAME_CURRENT,
                new String[]{userName},
                where, whereArgs, null, null, null);
    }

    public Cursor getNumSleep(String user) {
        SQLiteDatabase database = getWritableDatabase();
        String where = "username = ?";
        String[] whereArgs = new String[] {user};

        return database.query(TABLE_NAME_SLEEP,
                new String[]{num_Hours},
                where, whereArgs, null, null, null);

    }


    public boolean updateLogin(ContentValues contentValues, String user) {
        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_NAME_CURRENT, contentValues, "username = ?", new String[] {user});
        return true;
    }

    public boolean defaultFalse() {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(logged_in, "false");
        database.update(TABLE_NAME_CURRENT, values, null, null);
        return true;
    }

    public boolean updateAcc(ContentValues contentValues, String user) {
        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_NAME, contentValues, "username = ?", new String[] {user});
        return true;
    }

    public int delete_to_do(String user){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME_TO_DO, userName_to_do + " = ?",
                new String[]{user});
    }

    public int delete_calendar(String user){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME_CALENDAR, userName_calendar + " = ?",
                new String[]{user});
    }

    public int delete_water(String user){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME_WATER, userName_water + " = ?",
                new String[]{user});
    }

    public int delete_sleep(String user){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME_SLEEP, userName_sleep + " = ?",
                new String[]{user});
    }

    public int delete_grocery(String user){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(TABLE_NAME_GROCERY, userName_grocery + " = ?",
                new String[]{user});
    }
}
