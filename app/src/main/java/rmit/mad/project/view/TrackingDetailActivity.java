package rmit.mad.project.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import rmit.mad.project.R;
import rmit.mad.project.service.TrackableService;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.util.DateTimePicker;
import rmit.mad.project.util.IDateTimePickerListener;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public abstract class TrackingDetailActivity extends AppCompatActivity implements IDateTimePickerListener {

    private static final String TAG = TrackingDetailActivity.class.getName();
    protected TextView trackableTextView;
    protected EditText titleView;
    protected TextView startView;
    protected TextView endView;
    protected TextView locationView;
    protected EditText meetingLocationView;
    protected EditText meetingTimeView;
    protected Button saveTrackingBtn;
    protected Trackable trackable;
    protected Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracking_detail);

        intent = getIntent();

        String trackableId = intent.getStringExtra(TRACKABLE_ID.name());
        trackable = TrackableService.getInstance().getById(trackableId);

        trackableTextView = findViewById(R.id.trackable);
        trackableTextView.setText(getString(R.string.tracking_trackable_title, trackable.getName()));

        titleView = findViewById(R.id.title);
        startView = findViewById(R.id.start);
        endView = findViewById(R.id.finish);
        locationView = findViewById(R.id.actual_location);
        meetingLocationView = findViewById(R.id.meet_location);
        meetingTimeView = findViewById(R.id.meet_time);
        meetingTimeView.setOnClickListener( new DateTimePicker(this));

        saveTrackingBtn = findViewById(R.id.saveTrackingBtn);

        displayTrackingData();
    }


    public void onDateTimePicked(Date datePicked) {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        meetingTimeView.setText(df.format(datePicked));
    }

    /**
     * Fills the rest of the data that it is unknown a prior of the tracking in the view.
     */
    protected abstract void displayTrackingData();

}
