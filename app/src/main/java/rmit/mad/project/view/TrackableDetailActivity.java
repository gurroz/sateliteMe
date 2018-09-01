package rmit.mad.project.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import rmit.mad.project.R;
import rmit.mad.project.service.RouteInfoService;
import rmit.mad.project.service.TrackableService;
import rmit.mad.project.adapter.RouteInfoAdapter;
import rmit.mad.project.model.Trackable;

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
    private Trackable trackable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trackables_detail);

        String trackableId = getIntent().getStringExtra(TRACKABLE_ID.name());
        trackable = TrackableService.getInstance().getById(trackableId);

        nameView = findViewById(R.id.name);
        urlView = findViewById(R.id.url);
        categoryView = findViewById(R.id.category);
        descriptionView = findViewById(R.id.description);
        imageView = findViewById(R.id.trackableImage);


        routeAdapter = new RouteInfoAdapter(RouteInfoService.getInstance().getTrackableRouteInfoFromNow(this, trackableId, 100));

        routeLayoutManager = new LinearLayoutManager(this);
        routeRecyclerView = findViewById(R.id.routeInfoListView);
        routeRecyclerView.setHasFixedSize(true);
        routeRecyclerView.setLayoutManager(routeLayoutManager);
        routeRecyclerView.setAdapter(routeAdapter);

        updateView();
    }

    private void updateView() {
        nameView.setText(trackable.getName());
        categoryView.setText(trackable.getCategory());
        descriptionView.setText(trackable.getDescription());
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.foodtruck,null));
    }
}
