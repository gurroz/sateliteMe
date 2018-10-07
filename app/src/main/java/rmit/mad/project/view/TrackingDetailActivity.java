package rmit.mad.project.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.service.RouteInfoService;
import rmit.mad.project.service.TrackableService;
import rmit.mad.project.util.DateTimePicker;
import rmit.mad.project.util.IDateTimePickerListener;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public abstract class TrackingDetailActivity extends AppCompatActivity implements IDateTimePickerListener {

    private static final String TAG = TrackingDetailActivity.class.getName();
    protected TextView trackableTextView;
    protected EditText titleView;
    protected TextView startView;
    protected TextView endView;
    protected EditText meetingLocationView;
    protected EditText meetingTimeView;
    protected Button saveTrackingBtn;
    protected Trackable trackable;
    protected Intent intent;
    protected RouteInfo routeInfo;
    protected MapView mMapView;
    protected GoogleMap googleMap;

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
        meetingLocationView = findViewById(R.id.meet_location);
        meetingTimeView = findViewById(R.id.meet_time);
        meetingTimeView.setOnClickListener( new DateTimePicker(this));

        saveTrackingBtn = findViewById(R.id.saveTrackingBtn);

        mMapView = findViewById(R.id.mapViewTracking);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<RouteInfo> routesInfo = RouteInfoService.getInstance().getTrackableRouteInfoFromNow(this, trackable.getId(), 100);
        if(routesInfo.size() > 0) {
            routeInfo = routesInfo.get(0);
            Log.d("TRACKINGDETAIL","Route info is:" + routeInfo);

        } else {
            Log.d("TRACKINGDETAIL","No data fir routesInfo");
        }

        showMap();
        displayTrackingData();
    }


    public void onDateTimePicked(Date datePicked) {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        meetingTimeView.setText(df.format(datePicked));
    }


    protected void showMap() {

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            @SuppressLint("MissingPermission")
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMyLocationEnabled(true);
                LatLng location = null;

                // Asumes all data is correct, given that the file comes from us.
                // For dropping a marker at a point on the Map
                Double lat = Double.valueOf(routeInfo.getLocation().split(",")[0]);
                Double lng = Double.valueOf(routeInfo.getLocation().split(",")[1]);
                location = new LatLng(lat, lng);

                googleMap.addMarker(new MarkerOptions().position(location).title("Actual position"));

                // For zooming automatically to the location of the last marker
                if(location != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

            }
        });
    }

    /**
     * Fills the rest of the data that it is unknown a prior of the tracking in the view.
     */
    protected abstract void displayTrackingData();

}
