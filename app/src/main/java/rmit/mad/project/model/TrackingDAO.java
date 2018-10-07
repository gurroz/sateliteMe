package rmit.mad.project.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrackingDAO extends LocalStorage<Tracking> {

    private static TrackingDAO instance = new TrackingDAO();

    private TrackingDAO() { }

    public static TrackingDAO getInstance() {
        return instance;
    }

    public List<Tracking> getAllSortedByDate() {
        List<Tracking> resp = getAll();

        Collections.sort(resp, new TrackingSorting());
        return resp;
    }

    class TrackingSorting implements Comparator<Tracking> {
        @Override
        public int compare(Tracking t1, Tracking t2) {
            return t1.getMeetTime().compareTo(t2.getMeetTime());
        }
    }

    @Override
    public List<Tracking> getAll() {
        Log.d("TrackingDAO","getAll");

        List<Tracking> resp = new ArrayList<Tracking>(collectionMap.values());
        if(resp.size() == 0) {
            resp = dbInstance.getAllTrackings();
            saveAll(resp);
        } else {
            Log.d("TrackingDAO","getAll from memory");
        }

        Log.d("TrackingDAO","getAll " + resp.size());

        return resp;
    }

    @Override
    public void persistToDB() {
        Log.d("TrackingDAO","persistToDB");
        dbInstance.saveAllTracking(getAll());
    }

    @Override
    protected String getId(Tracking entity) {
        return entity.getId();
    }

}
