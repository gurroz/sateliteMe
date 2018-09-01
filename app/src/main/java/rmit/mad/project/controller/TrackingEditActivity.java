package rmit.mad.project.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;
import rmit.mad.project.model.TrackingService;

import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;

public class TrackingEditActivity extends TrackingDetailActivity {

    private static final String TAG = TrackingDetailActivity.class.getName();

    protected Button deleteTrackingBtn;
    private Tracking tracking;

    public TrackingEditActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void displayData() {
        String trackingId = intent.getStringExtra(TRACKING_ID.name());
        tracking = TrackingDAO.getInstance().getById(trackingId);

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        this.titleView.setText(tracking.getTitle());
        this.startView.setText(df.format(tracking.getTargetStartTime()));
        this.endView.setText(df.format(tracking.getTargetFinishTime()));
        this.locationView.setText(tracking.getActualLocation());
        this.meetingLocationView.setText(tracking.getMeetLocation());
        this.meetingTimeView.setText(df.format(tracking.getMeetTime()));

        this.deleteTrackingBtn = findViewById(R.id.deleteTrackingBtn);
        this.deleteTrackingBtn.setVisibility(View.VISIBLE);
        this.deleteTrackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOnClick(v);
            }
        });
    }

    @Override
    protected Tracking getTracking() {
        return tracking;
    }

    private void deleteOnClick(final View view) {
        TrackingDAO.getInstance().delete(tracking.getId());
        ((Activity)view.getContext()).finish();
    }

    private List<RouteInfo> getRouteInfo() {
        Date actualDate = new Date();
        int searchWindow = 120;

        List<TrackingService.TrackingInfo> routesInfo = TrackingService.getSingletonInstance(this).getTrackingInfoForTimeRange(actualDate, searchWindow, 0 );
        List<RouteInfo> routesInfoFiltered = new ArrayList<>();
        for(TrackingService.TrackingInfo routeInfo : routesInfo) {
            if(trackableId == routeInfo.trackableId) {
                routesInfoFiltered.add(new RouteInfo(routeInfo.trackableId, routeInfo.date, routeInfo.stopTime, routeInfo.latitude, routeInfo.longitude));
            }
        }

        return routesInfoFiltered;
    }
}
