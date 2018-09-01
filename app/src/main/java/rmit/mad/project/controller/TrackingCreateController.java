package rmit.mad.project.controller;

import android.content.Intent;
import android.view.View;

import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.view.TrackingCreateActivity;

import static rmit.mad.project.enums.IntentModelEnum.ROUTE_INFO;
import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public class TrackingCreateController implements View.OnClickListener {

    private RouteInfo routeInfo;

    public TrackingCreateController(RouteInfo routeInfo) {
        this.routeInfo = routeInfo;
    }

    @Override
    public void onClick(View v) {
        createTracking(v);
    }



    public void createTracking(final View view) {
        Intent intent = new Intent(view.getContext(), TrackingCreateActivity.class);

        intent.putExtra(TRACKABLE_ID.name(), routeInfo.getTrackableId());
        intent.putExtra(ROUTE_INFO.name(), routeInfo);

        view.getContext().startActivity(intent);
    }
}
