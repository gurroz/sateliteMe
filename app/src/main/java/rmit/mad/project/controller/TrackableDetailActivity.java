package rmit.mad.project.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfoAdapter;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableAdapter;
import rmit.mad.project.model.TrackableDAO;
import rmit.mad.project.model.TrackableService;
import rmit.mad.project.model.TrackingService;

public class TrackableDetailActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView urlView;
    private TextView categoryView;
    private TextView descriptionView;
    private ImageView imageView;
    private Button addTrackingButton;

    private RecyclerView routeRecyclerView;
    private RecyclerView.Adapter routeAdapter;
    private RecyclerView.LayoutManager routeLayoutManager;
    private int trackableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trackables_detail);

        trackableId = getIntent().getIntExtra("TRACKABLE_ID", 0);
        Trackable trackable = TrackableDAO.getInstance().getTrackableById(trackableId);

        nameView = findViewById(R.id.name);
        urlView = findViewById(R.id.url);

        nameView.setText(trackable.getName());
        urlView.setText(trackable.getUrl());

        categoryView = findViewById(R.id.category);
        categoryView.setText(trackable.getCategory());

        descriptionView = findViewById(R.id.description);
        descriptionView.setText(trackable.getDescription());

        imageView = findViewById(R.id.trackableImage);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.foodtruck,null));

        addTrackingButton = findViewById(R.id.addTracking);
        addTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReal(v, trackableId);
            }
        });

        routeLayoutManager = new LinearLayoutManager(this);

        routeAdapter = new RouteInfoAdapter(getRouteInfo());

        routeRecyclerView = findViewById(R.id.routeInfoListView);
        routeRecyclerView.setHasFixedSize(true);
        routeRecyclerView.setLayoutManager(routeLayoutManager);
        routeRecyclerView.setAdapter(routeAdapter);
    }


    private void onClickReal(final View view, final int trackableId) {
        Intent intent = new Intent(view.getContext(), TrackingDetailActivity.class);
        intent.putExtra("TRACKABLE_ID", trackableId);
        view.getContext().startActivity(intent);
    }

    private List<TrackingService.TrackingInfo> getRouteInfo() {
        Date actualDate = new Date();
        int searchWindow = 120;

        List<TrackingService.TrackingInfo> routesInfo = TrackingService.getSingletonInstance(this).getTrackingInfoForTimeRange(actualDate, searchWindow, 0 );
        List<TrackingService.TrackingInfo> routesInfoFiltered = new ArrayList<>();
        for(TrackingService.TrackingInfo routeInfo : routesInfo) {
            Log.d("Assigment", "trackableId is: "+ trackableId);
            Log.d("Assigment", "routeInfo is: "+ routeInfo.trackableId);
            if(trackableId == routeInfo.trackableId) {
                routesInfoFiltered.add(routeInfo);
            }
        }

        return routesInfoFiltered;
    }
}
