package rmit.mad.project.model;

public class TrackableDAO extends LocalStorage<Trackable> {

    private static TrackableDAO instance = new TrackableDAO();
    private TrackableDAO() { }

    public static TrackableDAO getInstance() {
        return instance;
    }
}
