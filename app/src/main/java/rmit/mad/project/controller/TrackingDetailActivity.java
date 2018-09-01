package rmit.mad.project.controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rmit.mad.project.R;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableDAO;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public abstract class TrackingDetailActivity extends AppCompatActivity {

    private static final String TAG = TrackingDetailActivity.class.getName();
    protected TextView trackableTextView;
    protected EditText titleView;
    protected TextView startView;
    protected TextView endView;
    protected TextView locationView;
    protected EditText meetingLocationView;
    protected EditText meetingTimeView;
    protected Button saveTrackingBtn;
    protected int trackableId;
    protected Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracking_detail);

        intent = getIntent();

        trackableId = intent.getIntExtra(TRACKABLE_ID.name(), 0);
        Trackable trackable = TrackableDAO.getInstance().getById(String.valueOf(trackableId));

        trackableTextView = findViewById(R.id.trackable);
        trackableTextView.setText(getString(R.string.tracking_trackable_title, trackable.getName()));

        titleView = findViewById(R.id.title);
        startView = findViewById(R.id.start);
        endView = findViewById(R.id.finish);
        locationView = findViewById(R.id.actual_location);
        meetingLocationView = findViewById(R.id.meet_location);
        meetingTimeView = findViewById(R.id.meet_time);
        meetingTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(v.getContext());
            }
        });

        saveTrackingBtn = findViewById(R.id.saveTrackingBtn);

        saveTrackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnClick(v);
            }
        });

       displayData();
    }

    private Tracking fillTrackinData(Tracking tracking) throws ParseException {
        String title = titleView.getText().toString();
        String meetingLocation = meetingLocationView.getText().toString();
        String meetingTime = meetingTimeView.getText().toString();

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

        tracking.setTitle(title);
        tracking.setMeetTime(df.parse(meetingTime));
        tracking.setMeetLocation(meetingLocation);
        tracking.setIdTrackable(trackableId);

        return tracking;
    }

    private void saveOnClick(final View view) {
        try {
            Tracking tracking = getTracking();
            tracking = fillTrackinData(tracking);
            if(tracking.validateDates()) {
                TrackingDAO.getInstance().save(tracking.getId(), tracking);
                ((Activity)view.getContext()).finish();
            } else {
                Toast.makeText(view.getContext(), R.string.tracking_meeting_time_error, Toast.LENGTH_LONG).show();
            }

        } catch (ParseException e) {
            Log.e(TAG, "Error parsing target or start times: {}", e);
        }
    }

    public void showDateTimePicker(Context context) {
        final Calendar currentDate = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v(TAG, "The choosen one " + date.getTime());

                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

                        meetingTimeView.setText(df.format(date.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    protected abstract void displayData();
    protected abstract Tracking getTracking();
}
