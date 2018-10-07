package rmit.mad.project.view;

import android.os.Bundle;

import rmit.mad.project.controller.TrackingSaveController;
import rmit.mad.project.model.RouteInfo;

import static rmit.mad.project.enums.IntentModelEnum.ROUTE_INFO;

public class TrackingCreateActivity extends TrackingDetailActivity implements ITrackingSaver{

    private String startDate;
    private String endDate;
    private String actualLocation;
    private RouteInfo routeInfo;


    public TrackingCreateActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeInfo = intent.getParcelableExtra(ROUTE_INFO.name());
    }


    @Override
    /**
     * This case is when no Tracking is persisted, so the parcial data comes from the selected RouteInfo
     */
    protected void displayTrackingData() {
        startDate = routeInfo.getStartDate();
        endDate = routeInfo.getEndDate();
        actualLocation = routeInfo.getLocation();

        this.startView.setText(startDate);
        this.endView.setText(endDate);
        this.locationView.setText(actualLocation);

        saveTrackingBtn.setOnClickListener(new TrackingSaveController(this, null));
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
