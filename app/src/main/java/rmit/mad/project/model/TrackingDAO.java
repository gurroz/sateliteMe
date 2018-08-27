package rmit.mad.project.model;

import java.util.ArrayList;
import java.util.List;

public class TrackingDAO {

    private static List<Tracking> trackingList = new ArrayList<Tracking>();
    private static TrackingDAO instance = new TrackingDAO();
    private TrackingDAO() { }

    public static TrackingDAO getInstance() {
        return instance;
    }

    public List<Tracking> getTracking() {
        return trackingList;
    }

    public void persistTracking(Tracking tracking) {
        trackingList.add(tracking);
    }

    public Tracking getTracking(String id) {
        Tracking resp = null;
        for(Tracking tracking : trackingList) {
            if(tracking.getId().equals(id)) {
                resp = tracking;
                break;
            }
        }
        return resp;
    }


    public void deleteTracking(Tracking tracking) {
        trackingList.remove(tracking);
    }
}
