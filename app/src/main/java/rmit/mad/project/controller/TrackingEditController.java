package rmit.mad.project.controller;

import android.content.Intent;
import android.view.View;

import rmit.mad.project.view.TrackingEditActivity;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;
import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;

public class TrackingEditController implements View.OnClickListener {

    private String idTrackable;
    private String idTracking;

    public TrackingEditController(String idTrackable, String idTracking) {
        this.idTrackable = idTrackable;
        this.idTracking = idTracking;
    }

    @Override
    public void onClick(View v) {
        trackingDetail(v);
    }


    public void trackingDetail(final View view) {
        Intent intent = new Intent(view.getContext(), TrackingEditActivity.class);
        intent.putExtra(TRACKING_ID.name(), idTracking);
        intent.putExtra(TRACKABLE_ID.name(), idTrackable);
        view.getContext().startActivity(intent);
    }
}
