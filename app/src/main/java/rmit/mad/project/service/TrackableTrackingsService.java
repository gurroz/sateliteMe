package rmit.mad.project.service;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Observable;

import rmit.mad.project.model.Meeting;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;

public class TrackableTrackingsService extends Observable {

    private static final String TAG = TrackableTrackingsService.class.getName();

    private static class LazyHolder {
        static final TrackableTrackingsService instance = new TrackableTrackingsService();
    }

    private TrackableTrackingsService() {
    }

    public static TrackableTrackingsService getInstance() {
        return LazyHolder.instance;
    }

    public void getAllSortedByDate() {
        notifyChangesInList();
    }

    public Tracking getTrackingById(String trackingId) {
        return TrackingDAO.getInstance().getById(trackingId);
    }

    public void deleteTrackingById(String trackingId) {
        TrackingDAO.getInstance().delete(trackingId);
        notifyChangesInList();
    }

    public boolean saveTracking(String idTracking, String trackableId, String startDate, String endDate,
                                   String actualLocation, String title, String meetingTime, String meetingLocation) {

        Tracking tracking = new Meeting();
        try {
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            tracking.setTargetStartTime(df.parse(startDate));
            tracking.setTargetFinishTime(df.parse(endDate));
            tracking.setActualLocation(actualLocation);
            tracking.setTitle(title);
            tracking.setMeetTime(df.parse(meetingTime));
            tracking.setMeetLocation(meetingLocation);
            tracking.setIdTrackable(trackableId);

            tracking.setId(idTracking);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing target or start times: {}", e);
            return false;
        }

        return checkSaveTracking(tracking);
    }

    private void notifyChangesInList() {
        setChanged();
        notifyObservers(obtainAllSortedByDate());
    }

    private List<Tracking> obtainAllSortedByDate() {
        return TrackingDAO.getInstance().getAllSortedByDate();
    }

    private boolean checkSaveTracking(Tracking tracking) {
        boolean resp = false;
        if(tracking.validateDates()) {
            TrackingDAO.getInstance().save(tracking.getId(), tracking);
            resp = true;
        }

        notifyChangesInList();

        return resp;
    }
}