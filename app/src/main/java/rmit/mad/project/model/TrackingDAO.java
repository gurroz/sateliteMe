package rmit.mad.project.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rmit.mad.project.model.Database.DatabaseHelper;

import static android.content.ContentValues.TAG;

public class TrackingDAO extends LocalStorage<Tracking> {

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
    public static final String TRUCK_ACTUAL_LOC = "ACTUAL_LOCATION";
    public static final String MEETING_LOCATION = "MEET_LOCATION";

    private static TrackingDAO instance = new TrackingDAO();

    private TrackingDAO() { }

    public static TrackingDAO getInstance() {
        return instance;
    }

    public List<Tracking> getAllSortedByDate() {
        List<Tracking> resp = getAll();
        //List<Tracking> resp = getFromDatabase(); DatabaseHelper parameter needed

        Collections.sort(resp, new TrackingSorting());
        return resp;
    }

    @Override
    public List<Tracking> getFromDatabase(DatabaseHelper dbh) {
        String sql = "SELECT * FROM " + TABLE_EVENTS + " ";
        List<Tracking> dbTrackings = new ArrayList<>();
        Cursor cursor = dbh.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Tracking event = new Meeting();
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
                event.setId(cursor.getString(cursor.getColumnIndex(MD5ID)));
                int id = cursor.getInt(cursor.getColumnIndex(ASSIGNED_TRACKABLE_ID));
                event.setIdTrackable(String.valueOf(id));
                event.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                try {
                    event.setTargetStartTime(df.parse(cursor.getString(cursor.getColumnIndex(EVENT_START_TIME))));
                    event.setTargetFinishTime(df.parse(cursor.getString(cursor.getColumnIndex(EVENT_FINISH_TIME))));
                    event.setMeetTime(df.parse(cursor.getString(cursor.getColumnIndex(MEET_TIME))));
                } catch (ParseException e) {
                    Log.e(TAG, "Error parsing target or start times: {}", e);
                    event.setTargetStartTime(null);
                }
                event.setActualLocation(cursor.getString(cursor.getColumnIndex(TRUCK_ACTUAL_LOC)));
                event.setMeetLocation(cursor.getString(cursor.getColumnIndex(MEETING_LOCATION)));
                cursor.moveToNext();
            }


        }

        cursor.close();
        return dbTrackings;
    }

    @Override
    public void addToDatabase(DatabaseHelper dbh, String id, Tracking event) {
        ContentValues cv = new ContentValues();
        cv.put(MD5ID, event.getId());
        cv.put(ASSIGNED_TRACKABLE_ID, event.getIdTrackable());
        cv.put(TITLE, event.getTitle());
        cv.put(EVENT_START_TIME, event.getTargetStartTime().toString());
        cv.put(EVENT_FINISH_TIME, event.getTargetFinishTime().toString());
        cv.put(MEET_TIME, event.getMeetTime().toString());
        cv.put(MEETING_LOCATION, event.getMeetLocation());
        cv.put(TRUCK_ACTUAL_LOC, event.getActualLocation());//should it be stored in db??

        dbh.getWritableDatabase().insert(TABLE_EVENTS, null, cv);
    }

    @Override
    public String getIdFromObject(DatabaseHelper dbh, Tracking tracking) { //I don't quit understand how this method should work
        String sql = "SELECT " + EVENT_ID + " FROM " + TABLE_EVENTS + " WHERE " + MD5ID + "=?" +  ";";
        String trackingID = "";
        Cursor cursor = dbh.getReadableDatabase().rawQuery(sql, new String[]{tracking.getId()});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            trackingID = cursor.getString(cursor.getColumnIndex(TRACKABLE_ID));
        }
        cursor.close();
        return trackingID;
    }

    class TrackingSorting implements Comparator<Tracking> {
        @Override
        public int compare(Tracking t1, Tracking t2) {
            return t1.getMeetTime().compareTo(t2.getMeetTime());
        }
    }

//    @Override
//    public void persistDatabase() {
//        List<Tracking> resp = getAll();
//
//        database.clear();
//        for( List<Tracking> resp ){
//            database.add(resp)         // Call DB service to insert data
//
//        }
//    }



    public boolean deleteTrackingFromBD(DatabaseHelper dbh, String trackingID) {
        String where = MD5ID + "=?";
        return dbh.getWritableDatabase().delete(TABLE_EVENTS, where, new String[]{trackingID}) > 0;
    }


}
