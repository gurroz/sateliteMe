package rmit.mad.project.model;

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
        return null;
    }

    @Override
    public List<Trackable> sevaToDatabase(String id, Trackable trackable) {
        return null;
    }

    @Override
    public String getIdFromObject(Object e) {
        return null;
    }

    public boolean isDBInitiated() {
        return false; // Call Db service to check
    }
}
