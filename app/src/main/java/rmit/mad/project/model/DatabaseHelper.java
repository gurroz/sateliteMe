package rmit.mad.project.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GeoTracking.db";
    public static final int DATABASE_VERSION = 1;


    //table names
    public static final String TABLE_TRACKABLES = "tbl_trackables";
    public static final String TABLE_TRACKINGS = "tbl_trackings";

    //column names for trackables table
    public static final String TRACKABLE_ID = "ID";
    public static final String TRACKABLE_NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String URL = "URL";
    public static final String CATEGORY = "CATEGORY";

    //column names for tracking table
    public static final String EVENT_ID = "ID";
    public static final String ASSIGNED_TRACKABLE_ID = "TRACKABLE_ID";
    public static final String TITLE = "TITLE";
    public static final String EVENT_START_TIME = "START_TIME";
    public static final String EVENT_FINISH_TIME = "FINISH_TIME";
    public static final String MEET_TIME = "MEET_TIME";
    public static final String MEETING_LOCATION = "MEET_LOCATION";

    public static final String CREATE_TABLE_TRACKABLES = "CREATE TABLE " + TABLE_TRACKABLES + " ( " + TRACKABLE_ID + " TEXT PRIMARY KEY, " + TRACKABLE_NAME + " TEXT, " + DESCRIPTION + " TEXT, " +
            URL + " TEXT, " + CATEGORY  + " TEXT " + " );";
    public static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_TRACKINGS + " ( " + EVENT_ID + " TEXT PRIMARY KEY, " + ASSIGNED_TRACKABLE_ID + " INTEGER, " + TITLE + " TEXT, " +
            EVENT_START_TIME + " TEXT, " + EVENT_FINISH_TIME + " TEXT, " + MEET_TIME + " TEXT, " + MEETING_LOCATION + " TEXT " + " );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Trackable> getAllTrackables() {
        String sql = "SELECT * FROM " + TABLE_TRACKABLES + ";";
        List<Trackable> dbTrackables = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Trackable truck = new FoodTruck();
                truck.setId(cursor.getString(cursor.getColumnIndex(TRACKABLE_ID)));
                truck.setName(cursor.getString(cursor.getColumnIndex(TRACKABLE_NAME)));
                truck.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));
                truck.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                truck.setUrl(cursor.getString(cursor.getColumnIndex(URL)));

                dbTrackables.add(truck);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return dbTrackables;
    }

    public void addTrackable(Trackable trackable) {
        ContentValues cv = new ContentValues();

        cv.put(TRACKABLE_ID, trackable.getId());
        cv.put(TRACKABLE_NAME, trackable.getName());
        cv.put(DESCRIPTION, trackable.getDescription());
        cv.put(URL, trackable.getUrl());
        cv.put(CATEGORY, trackable.getCategory());

        this.getWritableDatabase().insert(TABLE_TRACKABLES, null, cv);
    }

    public List<Tracking> getAllTracking() {
        String sql = "SELECT * FROM " + TABLE_TRACKINGS + ";";
        List<Tracking> dbTrackings = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                Tracking event = new Meeting();
                event.setId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                event.setIdTrackable(cursor.getString(cursor.getColumnIndex(ASSIGNED_TRACKABLE_ID)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));

                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
                try {
                    event.setTargetStartTime(df.parse(cursor.getString(cursor.getColumnIndex(EVENT_START_TIME))));
                    event.setTargetFinishTime(df.parse(cursor.getString(cursor.getColumnIndex(EVENT_FINISH_TIME))));
                    event.setMeetTime(df.parse(cursor.getString(cursor.getColumnIndex(MEET_TIME))));
                } catch (ParseException e) {
                    Log.e(TAG, "Error parsing target or start times: {}", e);
                    event.setTargetStartTime(null);
                }
                event.setMeetLocation(cursor.getString(cursor.getColumnIndex(MEETING_LOCATION)));

                dbTrackings.add(event);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return dbTrackings;
    }

    public void addTracking(Tracking tracking) {
        ContentValues cv = new ContentValues();
        cv.put(EVENT_ID, tracking.getId());
        cv.put(ASSIGNED_TRACKABLE_ID, tracking.getIdTrackable());
        cv.put(TITLE, tracking.getTitle());

        cv.put(MEETING_LOCATION, tracking.getMeetLocation());

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        cv.put(MEET_TIME, df.format(tracking.getMeetTime()));
        cv.put(EVENT_START_TIME, df.format(tracking.getTargetStartTime()));
        cv.put(EVENT_FINISH_TIME,df.format( tracking.getTargetFinishTime()));

        long inserted = this.getWritableDatabase().insert(TABLE_TRACKINGS, null, cv);
    }

    public void clearTrackings() {
       this.getWritableDatabase().delete(TABLE_TRACKINGS, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRACKABLES);
        db.execSQL(CREATE_TABLE_EVENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
