package rmit.mad.project.model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import rmit.mad.project.model.TrackableDAO;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    TrackableDAO trackableDAO = TrackableDAO.getInstance();

    public static final String DATABASE_NAME = "GeoTracking.db";
    //table names
    public static final String TABLE_TRACKABLES = "tbl_trackables";
    public static final String TABLE_EVENTS = "tbl_events";
    //column names for trackables table
    public static final String TRACKABLE_ID = "ID";
    public static final String TRACKABLE_NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String URL = "URL";
    public static final String CATEGORY = "CATEGORY";
    //public static final String COLUMN_NAME_MEDIA = "MEDIA"; //should I remove this from database??
    //column names for events table

    public static final String EVENT_ID = "ID";
    public static final String MD5ID = "MD5ID"; //pls check
    public static final String ASSIGNED_TRACKABLE_ID = "TRACKABLE_ID";
    public static final String TITLE = "TITLE";
    public static final String EVENT_START_TIME = "START_TIME";
    public static final String EVENT_FINISH_TIME = "FINISH_TIME";
    public static final String MEET_TIME = "MEET_TIME";
    public static final String TRUCK_ACTUAL_LOC = "ACTUAL_LOCATION"; //should it be stored in db??
    public static final String MEETING_LOCATION = "MEET_LOCATION";

    String file = "res/raw/food_truck_data.txt";
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(file);

    public static final String CREATE_TABLE_TRACKABLES = "CREATE TABLE " + TABLE_TRACKABLES + " ( " + TRACKABLE_ID + " INTEGER PRIMARY KEY, " + TRACKABLE_NAME + " TEXT, " + DESCRIPTION + " TEXT, " +
            URL + " TEXT, " + CATEGORY  + " TEXT " + " );";
    public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + " ( " + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MD5ID + " TEXT, " + ASSIGNED_TRACKABLE_ID + " INTEGER, " + TITLE + " TEXT, " +
            EVENT_START_TIME + " TEXT, " + EVENT_FINISH_TIME + " TEXT, " + MEET_TIME + " TEXT, " + TRUCK_ACTUAL_LOC + " TEXT, " + MEET_TIME + " TEXT, " + MEETING_LOCATION + " TEXT " + " );";

    private static DatabaseHelper dbSingletonInstance;

    public static DatabaseHelper getSingletonInstance(Context context) {
        if (dbSingletonInstance == null)
            dbSingletonInstance = new DatabaseHelper(
                    context.getApplicationContext());
        return dbSingletonInstance;
    }


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRACKABLES);
        db.execSQL(CREATE_TABLE_EVENTS);
        trackableDAO.persistDatabase(this, stream); //will it work using singleton here?
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKABLES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
    }

}
