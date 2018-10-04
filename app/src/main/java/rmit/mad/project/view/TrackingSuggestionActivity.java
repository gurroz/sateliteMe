package rmit.mad.project.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rmit.mad.project.R;
import rmit.mad.project.controller.TrackingSaveController;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.service.RouteInfoService;


public class TrackingSuggestionActivity extends AppCompatActivity {

    private Button acceptSuggestionBtn;
    private Button nextSuggestionBtn;
    private Button cancelSuggestionBtn;
    private TextView trackableNameView;
    private TextView trackableTypeView;
    private TextView trackableLocationView;
    private TextView trackableStoppedTimeView;
    private TextView trackableDistanceView;
    private RouteInfo routeInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_suggestion);

        acceptSuggestionBtn = findViewById(R.id.sugg_accept_btn);
        nextSuggestionBtn = findViewById(R.id.sugg_next_btn);
        cancelSuggestionBtn = findViewById(R.id.sugg_cancel_btn);
        trackableNameView = findViewById(R.id.sugg_trackable_name);
        trackableTypeView = findViewById(R.id.sugg_trackable_type);
        trackableLocationView = findViewById(R.id.sugg_location);
        trackableStoppedTimeView = findViewById(R.id.sugg_time_stopped);
        trackableDistanceView = findViewById(R.id.sugg_distance);

        cancelSuggestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteInfoService.getInstance().clearSuggestedRoutesInfo();
                finish();
            }
        });

        nextSuggestionBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRouteInfoData();
            }
        });

        displayRouteInfoData();

        acceptSuggestionBtn.setOnClickListener(
                new TrackingSaveController(null, routeInfo.getTrackableId(),
                        "Meeting " + routeInfo.getTrackableName(),
                        routeInfo.getStartDate(), routeInfo.getEndDate(),
                        routeInfo.getLocationName(), routeInfo.getMeetingTime(), ""));

    }

    private void displayRouteInfoData() {
        routeInfo = RouteInfoService.getInstance().getSuggestedRoutesInfo();
        if(routeInfo != null) {
            Log.d("Suggestion", "Showin sug: " + routeInfo.toString());
            trackableNameView.setText(routeInfo.getTrackableName());
            trackableTypeView.setText(routeInfo.getTrackableType());
            trackableLocationView.setText(routeInfo.getLocationName());
            trackableStoppedTimeView.setText(routeInfo.getMeetingTime());
            trackableDistanceView.setText(routeInfo.getDistanceTime());
        } else {
            finish();
        }
    }

}
