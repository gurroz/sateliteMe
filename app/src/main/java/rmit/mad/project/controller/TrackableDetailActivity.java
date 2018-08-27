package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import rmit.mad.project.R;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableDAO;
import rmit.mad.project.model.TrackableService;

public class TrackableDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trackables_detail);

        String trackableId = getIntent().getStringExtra("TRACKABLE_ID");
        Trackable trackable = TrackableDAO.getInstance().getTrackableById(trackableId);

        TextView nameView = findViewById(R.id.name);
        nameView.setText(trackable.getName());

        TextView urlView = findViewById(R.id.url);
        urlView.setText(trackable.getUrl());

        TextView category = findViewById(R.id.category);
        category.setText(trackable.getCategory());

        TextView descriptionView = findViewById(R.id.description);
        descriptionView.setText(trackable.getDescription());

        ImageView imageView = findViewById(R.id.trackableImage);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.foodtruck,null));

    }

}
