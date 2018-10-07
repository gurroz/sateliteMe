package rmit.mad.project.controller;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.service.TrackableTrackingsService;
import rmit.mad.project.view.ITrackingSaver;

public class TrackingSaveController implements View.OnClickListener {

    private String trackingId;
    private ITrackingSaver routeFiller;

    public TrackingSaveController(ITrackingSaver routeFiller, String trackingId) {
        this.routeFiller = routeFiller;
        this.trackingId = trackingId;
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

        RouteInfo routeInfo = routeFiller.getDataForTrackingCreation();

        if(TrackableTrackingsService.getInstance().saveTracking(trackingId, routeInfo.getTrackableId(), routeInfo.getStartDate(), routeInfo.getEndDate(),
                "", routeInfo.getMeetingName(), routeInfo.getMeetingTime(), routeInfo.getLocationName())) {
            ((Activity)view.getContext()).finish();
        } else {
            Toast.makeText(view.getContext(), R.string.tracking_meeting_time_error, Toast.LENGTH_LONG).show();
        }
    }
}
