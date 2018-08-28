package rmit.mad.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackableDAO {

    private static Map<Integer, Trackable> trackableList = new HashMap<Integer, Trackable>();
    private static TrackableDAO instance = new TrackableDAO();
    private TrackableDAO() { }

    public static TrackableDAO getInstance() {
        return instance;
    }

    public List<Trackable> getTrackables() {
        return new ArrayList<Trackable>(trackableList.values());
    }

    public Trackable getTrackableById(int id) {
        return trackableList.get(id);
    }

    public Trackable persistTrackable(Trackable trackable) {
        return trackableList.put(trackable.getId(), trackable);
    }

    public void deleteTrackable(Trackable trackable) {
        trackableList.remove(trackable.getId());
    }
}
