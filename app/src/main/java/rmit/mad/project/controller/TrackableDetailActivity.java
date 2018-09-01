package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.RouteInfoAdapter;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableDAO;
import rmit.mad.project.model.TrackingService;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public class TrackableDetailActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView urlView;
    private TextView categoryView;
    private TextView descriptionView;
    private ImageView imageView;

    private RecyclerView routeRecyclerView;
    private RecyclerView.Adapter routeAdapter;
    private RecyclerView.LayoutManager routeLayoutManager;
    private int trackableId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trackables_detail);

        trackableId = getIntent().getIntExtra(TRACKABLE_ID.name(), 0);
        Trackable trackable = TrackableDAO.getInstance().getById(String.valueOf(trackableId));

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

        routeLayoutManager = new LinearLayoutManager(this);

        routeAdapter = new RouteInfoAdapter(getRouteInfo());

        routeRecyclerView = findViewById(R.id.routeInfoListView);
        routeRecyclerView.setHasFixedSize(true);
        routeRecyclerView.setLayoutManager(routeLayoutManager);
        routeRecyclerView.setAdapter(routeAdapter);
    }

    private List<RouteInfo> getRouteInfo() {
        Date actualDate = new Date();
        int searchWindow = 120;

        List<TrackingService.TrackingInfo> routesInfo = TrackingService.getSingletonInstance(this).getTrackingInfoForTimeRange(actualDate, searchWindow, 0 );
        List<RouteInfo> routesInfoFiltered = new ArrayList<>();
        for(TrackingService.TrackingInfo routeInfo : routesInfo) {
            if(trackableId == routeInfo.trackableId) {
                routesInfoFiltered.add(new RouteInfo(routeInfo.trackableId, routeInfo.date, routeInfo.stopTime, routeInfo.latitude, routeInfo.longitude));
            }
        }

        return routesInfoFiltered;
    }
}
