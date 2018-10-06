package rmit.mad.project.model;

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
    public void persistDatabase() {
//        List<Tracking> resp = getAll();
//
//        database.clear();
//        for( List<Tracking> resp ){
//            database.add(resp)         // Call DB service to insert data
//
//        }
    }

    @Override
    public List<Tracking> getFromDatabase() {
        return new ArrayList<Tracking>();
    }

    @Override
    public List<Tracking> sevaToDatabase(String id, Tracking tracking) {
        return new ArrayList<Tracking>();
    }

    @Override
    public String getIdFromObject(Object e) {
        return ((Tracking)e).getId();
    }


}
