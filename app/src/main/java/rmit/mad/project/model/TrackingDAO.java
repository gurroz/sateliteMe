package rmit.mad.project.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrackingDAO extends PersistanceService<Tracking>{

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
}
