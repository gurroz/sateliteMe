package rmit.mad.project.model;

import java.util.ArrayList;
import java.util.List;

public class TrackableDAO extends LocalStorage<Trackable> {

    private static TrackableDAO instance = new TrackableDAO();
    private TrackableDAO() { }

    public static TrackableDAO getInstance() {
        return instance;
    }

    @Override
    public void persistDatabase() {

    }

    @Override
    public List<Trackable> getFromDatabase() {
        return new ArrayList<Trackable>();
    }

    @Override
    public List<Trackable> sevaToDatabase(String id, Trackable trackable) {
        return new ArrayList<Trackable>();
    }

    @Override
    public String getIdFromObject(Object e) {
        return ((Trackable)e).getId();
    }

    public boolean isDBInitiated() {
        return false; // Call Db service to check
    }
}
