package rmit.mad.project.controller;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rmit.mad.project.R;
import rmit.mad.project.service.TrackableTrackingsService;

public class TrackingSaveController implements View.OnClickListener {

    private String idTracking;
    private String trackableId;
    private EditText titleView;
    private TextView startView;
    private TextView endView;
    private EditText meetingLocationView;
    private EditText meetingTimeView;
    private TextView actualLocationView;

    public TrackingSaveController(String idTracking, String trackableId, EditText titleView, TextView startView,
                                  TextView endView, EditText meetingLocationView, EditText meetingTimeView, TextView actualLocationView) {
        this.idTracking = idTracking;
        this.trackableId = trackableId;
        this.titleView = titleView;
        this.startView = startView;
        this.endView = endView;
        this.meetingLocationView = meetingLocationView;
        this.meetingTimeView = meetingTimeView;
        this.actualLocationView = actualLocationView;
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

        String title = titleView.getText().toString();
        String startDate = startView.getText().toString();
        String endDate = endView.getText().toString();
        String meetingLocation= meetingLocationView.getText().toString();
        String meetingTime = meetingTimeView.getText().toString();
        String actualLocation = actualLocationView.getText().toString();

        if(TrackableTrackingsService.getInstance().saveTracking(idTracking, trackableId, startDate, endDate,
                actualLocation, title, meetingTime, meetingLocation)) {
            ((Activity)view.getContext()).finish();
        } else {
            Toast.makeText(view.getContext(), R.string.tracking_meeting_time_error, Toast.LENGTH_LONG).show();
        }
    }
}
