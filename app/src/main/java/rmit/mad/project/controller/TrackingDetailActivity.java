package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import rmit.mad.project.R;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableDAO;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;
import rmit.mad.project.model.TrackingImp;

public class TrackingDetailActivity extends AppCompatActivity {

    private TextView trackableTextView;
    private EditText titleView;
    private EditText startView;
    private EditText endView;
    private EditText locationView;
    private EditText meetingLocationView;
    private EditText meetingTimeView;
    private Button saveTrackingBtn;
    private Button deleteTrackingBtn;
    private String trackableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracking_detail);

       final String trackingId = getIntent().getStringExtra("TRACKING_ID");
        Tracking tracking = TrackingDAO.getInstance().getTracking(trackingId);
        trackableId = getIntent().getStringExtra("TRACKABLE_ID");
        Trackable trackable = TrackableDAO.getInstance().getTrackableById(Integer.valueOf(trackableId));

        trackableTextView = findViewById(R.id.trackable);
        trackableTextView.setText(getString(R.string.tracking_trackable_title, trackable.getName()));

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

        deleteTrackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOnClick(v, trackingId);
            }
        });

    }

    private Tracking getTrackingData() throws ParseException {


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

    private void deleteOnClick(final View view, final String trackingId) {
        TrackingDAO.getInstance().deleteTrackingById(trackingId);
    }
}
