package rmit.mad.project.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rmit.mad.project.model.Database.DatabaseHelper;

import static android.content.ContentValues.TAG;

public class TrackableDAO extends LocalStorage<Trackable> {
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

    private static TrackableDAO instance = new TrackableDAO();
    private TrackableDAO() { }

    public static TrackableDAO getInstance() {
        return instance;
    }

    public boolean isDBInitiated() {
        //if ()
        return false; // Call Db service to check
    }

    public void persistDatabase(DatabaseHelper dbh, InputStream textFile) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(textFile));
            String line = reader.readLine();

            while (line != null) {
                String[] properties = line.split(",\"");

                Trackable truk = new FoodTruck();
                truk.setId(properties[0]);
                truk.setName(properties[1].replaceAll("\"", ""));
                truk.setDescription(properties[2].replaceAll("\"", ""));
                truk.setUrl(properties[3].replaceAll("\"", ""));
                truk.setCategory(properties[4].replaceAll("\"", ""));
                //media??
                addToDatabase(dbh, truk.getId(), truk);
                line = reader.readLine();
            }

        }
        catch (Exception e) {
            Log.i(TAG, "initTrackables: " + e.getMessage());
        }
    }

    @Override
    public List<Trackable> getFromDatabase(DatabaseHelper dbh) {
        String sql = "SELECT * FROM " + TABLE_TRACKABLES + ";";
        List<Trackable> dbTrackables = new ArrayList<>();
        Cursor cursor = dbh.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Trackable truck = new FoodTruck();
                int id = cursor.getInt(cursor.getColumnIndex(TRACKABLE_ID));
                truck.setId(String.valueOf(id));
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

    @Override
    public void addToDatabase(DatabaseHelper dbh, String id, Trackable truck) {
        ContentValues cv = new ContentValues();
        cv.put(TRACKABLE_ID, Integer.valueOf(id));//It is number, right?
        cv.put(TRACKABLE_NAME, truck.getName());
        cv.put(DESCRIPTION, truck.getDescription());
        cv.put(URL, truck.getUrl());
        //cv.put(COLUMN_NAME_MEDIA, truck.getMedia()); //null?
        cv.put(CATEGORY, truck.getCategory());

        dbh.getWritableDatabase().insert(TABLE_TRACKABLES, null, cv);
    }

    @Override
    public String getIdFromObject(DatabaseHelper dbh, Trackable trackable) { //I don't quit understand how this method should work
        String sql = "SELECT " + TRACKABLE_ID + " FROM " + TABLE_TRACKABLES + " WHERE " + TRACKABLE_NAME + "=?" +  ";";
        String trackableID = "";
        Cursor cursor = dbh.getReadableDatabase().rawQuery(sql, new String[]{trackable.getName()});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            trackableID = cursor.getString(cursor.getColumnIndex(TRACKABLE_ID));
        }
        return trackableID;
    }

}
