package rmit.mad.project.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class DatabaseHandler {

    private static DatabaseHandler instance;
    private DatabaseHelper db;

    private DatabaseHandler(Context context) {
        this.db = new DatabaseHelper(context);
    }

    public static DatabaseHandler getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseHandler(context);
        }
        return instance;
    }

    public void saveAllTrackables(List<Trackable> trackables) {
        for(Trackable trackable: trackables) {
            this.db.addTrackable(trackable);
        }
    }

    public List<Trackable> getAllTrackables() {
        return this.db.getAllTrackables();
    }


    public void saveAllTracking(List<Tracking> trackings) {
        this.db.clearTrackings();
        for (Tracking tracking : trackings) {
            this.db.addTracking(tracking);
        }
    }

    public List<Tracking> getAllTrackings() {
        return this.db.getAllTracking();
    }

    public boolean isDBInitiated() {
        return getAllTrackables().size() > 0;
    }
}
