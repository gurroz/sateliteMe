package rmit.mad.project.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;

import static rmit.mad.project.enums.IntentModelEnum.ROUTE_INFO;


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

        routeInfo = getIntent().getParcelableExtra(ROUTE_INFO.name());

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
                finish();
            }
        });
//
//        acceptSuggestionBtn.setOnClickListener((new TrackingSaveController(null, trackable.getId(), titleView,
//                startView, endView, meetingLocationView, meetingTimeView,locationView));

    }

}
