package rmit.mad.project.view;

import android.os.Bundle;

import rmit.mad.project.controller.TrackingSaveController;
import rmit.mad.project.model.RouteInfo;

import static rmit.mad.project.enums.IntentModelEnum.ROUTE_INFO;

public class TrackingCreateActivity extends TrackingDetailActivity {

    private String startDate;
    private String endDate;
    private String actualLocation;

    public TrackingCreateActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    /**
     * This case is when no Tracking is persisted, so the parcial data comes from the selected RouteInfo
     */
    protected void displayTrackingData() {
        RouteInfo routeInfo = intent.getParcelableExtra(ROUTE_INFO.name());

        startDate = routeInfo.getStartDate();
        endDate = routeInfo.getEndDate();
        actualLocation = routeInfo.getLocation();

        this.startView.setText(startDate);
        this.endView.setText(endDate);
        this.locationView.setText(actualLocation);

        saveTrackingBtn.setOnClickListener(new TrackingSaveController(null, trackable.getId(), titleView,
                startView, endView, meetingLocationView,meetingTimeView,locationView));
    }

}
