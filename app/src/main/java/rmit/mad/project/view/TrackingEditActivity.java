package rmit.mad.project.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.service.TrackableTrackingsService;
import rmit.mad.project.controller.TrackingDeleteController;
import rmit.mad.project.controller.TrackingSaveController;
import rmit.mad.project.model.Tracking;

import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;

public class TrackingEditActivity extends TrackingDetailActivity implements ITrackingSaver{

    protected Button deleteTrackingBtn;
    private RouteInfo routeInfo;

    public TrackingEditActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void displayTrackingData() {
        String trackingId = intent.getStringExtra(TRACKING_ID.name());
        Tracking tracking = TrackableTrackingsService.getInstance().getTrackingById(trackingId);

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        this.titleView.setText(tracking.getTitle());
        this.startView.setText(df.format(tracking.getTargetStartTime()));
        this.endView.setText(df.format(tracking.getTargetFinishTime()));
        this.locationView.setText(tracking.getActualLocation());
        this.meetingLocationView.setText(tracking.getMeetLocation());
        this.meetingTimeView.setText(df.format(tracking.getMeetTime()));

        this.deleteTrackingBtn = findViewById(R.id.deleteTrackingBtn);
        this.deleteTrackingBtn.setVisibility(View.VISIBLE);
        this.deleteTrackingBtn.setOnClickListener(new TrackingDeleteController(tracking.getId()));

        saveTrackingBtn.setOnClickListener(new TrackingSaveController(this, trackingId));
    }

    @Override
    public RouteInfo getDataForTrackingCreation() {
        routeInfo.setTrackableId( trackable.getId());
        routeInfo.setMeetingName(titleView.getText().toString());
        routeInfo.setStartDate(startView.getText().toString());
        routeInfo.setEndDate(endView.getText().toString());
        routeInfo.setLocation(meetingLocationView.getText().toString());
        routeInfo.setMeetingTime(meetingTimeView.getText().toString());

        return routeInfo;
    }
}
