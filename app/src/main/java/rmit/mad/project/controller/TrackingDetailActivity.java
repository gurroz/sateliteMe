package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import rmit.mad.project.R;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;
import rmit.mad.project.model.TrackingImp;

public class TrackingDetailActivity extends AppCompatActivity {

    private EditText titleView;
    private EditText startView;
    private EditText endView;
    private EditText locationView;
    private EditText meetingLocationView;
    private EditText meetingTimeView;
    private Button saveTrackingBtn;
    private Button deleteTrackingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracking_detail);

        String trackingId = getIntent().getStringExtra("TRACKING_ID");
        Tracking tracking = TrackingDAO.getInstance().getTracking(trackingId);

        titleView = findViewById(R.id.title);
        startView = findViewById(R.id.start);
        endView = findViewById(R.id.finish);
        locationView = findViewById(R.id.actual_location);
        meetingLocationView = findViewById(R.id.meet_time);
        meetingTimeView = findViewById(R.id.meet_location);

        saveTrackingBtn = findViewById(R.id.saveTrackingBtn);
        deleteTrackingBtn = findViewById(R.id.deleteTrackingBtn);

        if(tracking != null) {
            titleView.setText(tracking.getTitle());
            SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yy");
            startView.setText(df.format(tracking.getTargetStartTime()));
            endView.setText(df.format(tracking.getTargetFinishTime()));
            locationView.setText(tracking.getActualLocation());
        }


        saveTrackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnClick(v);
            }
        });

    }

    private Tracking getTrackingData() throws ParseException {

        String trackableId = getIntent().getStringExtra("TRACKABLE_ID");

        String title = titleView.getText().toString();
        String startDate = startView.getText().toString();
        String endDate = endView.getText().toString();
        String meetingLocation = meetingLocationView.getText().toString();
        String meetingTime = meetingTimeView.getText().toString();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yy");

        Tracking tracking = new TrackingImp();
        tracking.setTitle(title);
        tracking.setTargetStartTime(df.parse(startDate));
        tracking.setTargetFinishTime(df.parse(endDate));
        tracking.setMeetTime(df.parse(meetingTime));
        tracking.setMeetLocation(meetingLocation);
        tracking.setIdTrackable(trackableId);

        return tracking;
    }

    private void saveOnClick(final View view) {
        try {
            TrackingDAO.getInstance().persistTracking(getTrackingData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
