package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import rmit.mad.project.R;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableDAO;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;

public class TrackingDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tracking_detail);

        String trackingId = getIntent().getStringExtra("TRACKING_ID");
        Tracking tracking = TrackingDAO.getInstance().getTracking(trackingId);

        TextView nameView = findViewById(R.id.title);
        nameView.setText(tracking.getTitle());

        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yy");

        TextView startView = findViewById(R.id.start);
        startView.setText(df.format(tracking.getTargetStartTime()));

        TextView endView = findViewById(R.id.finish);
        endView.setText(df.format(tracking.getTargetFinishTime()));

        TextView locationView = findViewById(R.id.actual_location);
        locationView.setText(tracking.getActualLocation());

    }

}
