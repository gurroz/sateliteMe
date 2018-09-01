package rmit.mad.project.controller;

import android.app.Activity;
import android.view.View;

import rmit.mad.project.service.TrackableTrackingsService;

public class TrackingDeleteController implements View.OnClickListener {

    private String idTracking;

    public TrackingDeleteController(String idTracking) {
        this.idTracking = idTracking;
    }

    @Override
    public void onClick(View v) {
        deleteOnClick(v);
    }


    private void deleteOnClick(final View view) {
        TrackableTrackingsService.getInstance().deleteTrackingById(idTracking);
        ((Activity)view.getContext()).finish();
    }
}
