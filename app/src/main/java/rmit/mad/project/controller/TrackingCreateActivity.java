package rmit.mad.project.controller;

import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;

import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingImp;

import static rmit.mad.project.enums.IntentModelEnum.ROUTE_INFO;

public class TrackingCreateActivity extends TrackingDetailActivity {

    private static final String TAG = TrackingCreateActivity.class.getName();
    private String startDate;
    private String endDate;
    private String actualLocation;

    public TrackingCreateActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void displayData() {
        RouteInfo routeInfo = intent.getParcelableExtra(ROUTE_INFO.name());

        startDate = routeInfo.getStartDate();
        endDate = routeInfo.getEndDate();
        actualLocation = routeInfo.getLocation();

        this.startView.setText(startDate);
        this.endView.setText(endDate);
        this.locationView.setText(actualLocation);
    }

    @Override
    protected Tracking getTracking() {
        Tracking tracking = new TrackingImp();
        try {
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            tracking.setTargetStartTime(df.parse(startDate));
            tracking.setTargetFinishTime(df.parse(endDate));
            tracking.setActualLocation(actualLocation);
            tracking.setId();
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing target or start times: {}", e);
        }

        return tracking;
    }


}
