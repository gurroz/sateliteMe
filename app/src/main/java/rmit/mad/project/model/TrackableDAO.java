package rmit.mad.project.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TrackableDAO extends LocalStorage<Trackable> {

    private static TrackableDAO instance = new TrackableDAO();
    private TrackableDAO() { }

    public static TrackableDAO getInstance() {
        return instance;
    }

    @Override
    protected String getId(Trackable entity) {
        return entity.getId();
    }

    @Override
    public List<Trackable> getAll() {
        List<Trackable> resp = new ArrayList<Trackable>(collectionMap.values());
        if(resp.size() == 0) {
            resp = dbInstance.getAllTrackables();
            saveAll(resp);
        } else {
            Log.d("TrackableDAO","getAll from memory");
        }
        return resp;
    }

    @Override
    public void persistToDB() {
        dbInstance.saveAllTrackables(getAll());
    }

    public boolean isTrackableInitiated() {
        return dbInstance.isDBInitiated();
    }
}
