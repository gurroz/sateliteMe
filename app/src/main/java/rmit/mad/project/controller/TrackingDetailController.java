package rmit.mad.project.controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import rmit.mad.project.view.TrackableDetailActivity;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public class TrackingDetailController implements View.OnClickListener {

    private String id;
    public TrackingDetailController(String id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        trackingDetail(v);
    }


    public void trackingDetail(final View view) {
        Intent intent = new Intent(view.getContext(), TrackableDetailActivity.class);
        intent.putExtra(TRACKABLE_ID.name(), id);
        view.getContext().startActivity(intent);
    }
}
