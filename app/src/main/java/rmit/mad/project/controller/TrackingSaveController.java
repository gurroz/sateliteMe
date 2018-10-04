package rmit.mad.project.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import rmit.mad.project.R;
import rmit.mad.project.service.TrackableTrackingsService;

public class TrackingSaveController implements View.OnClickListener {

    private String idTracking;
    private String trackableId;
    private String title;
    private String startDate;
    private String endDate;
    private String meetingLocation;
    private String meetingTime;
    private String actualLocation;

    public TrackingSaveController(String idTracking, String trackableId, String title, String start,
                                  String end, String meetingLocation, String meetingTime, String actualLocation) {
        this.idTracking = idTracking;
        this.trackableId = trackableId;
        this.title = title;
        this.startDate = start;
        this.endDate = end;
        this.meetingLocation= meetingLocation;
        this.meetingTime = meetingTime;
        this.actualLocation = actualLocation;
    }


    @Override
    public void onClick(View v) {
        saveOnClick(v);
    }

    /**
     * Save Tracking information or shows errors
     * @param view
     */
    private void saveOnClick(final View view) {
        if(TrackableTrackingsService.getInstance().saveTracking(idTracking, trackableId, startDate, endDate,
                actualLocation, title, meetingTime, meetingLocation)) {
            ((Activity)view.getContext()).finish();
        } else {
            Toast.makeText(view.getContext(), R.string.tracking_meeting_time_error, Toast.LENGTH_LONG).show();
        }
    }
}
