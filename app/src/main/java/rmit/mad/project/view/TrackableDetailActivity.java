package rmit.mad.project.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.service.DistanceCalculationTask;
import rmit.mad.project.service.RouteInfoService;
import rmit.mad.project.service.TrackableService;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public class TrackableDetailActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView urlView;
    private TextView categoryView;
    private TextView descriptionView;
    private ImageView imageView;
    private Button getDistanceBtn;

    private Trackable trackable;

    MapView mMapView;
    private GoogleMap googleMap;
    List<RouteInfo> routesInfo;

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
        getDistanceBtn = findViewById(R.id.getDistance);

        // TODO: Change this to the RouteInfoService.getInstance().getTrackableRouteInfoFromNow for non testing running.
        routesInfo = RouteInfoService.getInstance().getTrackableRouteInfoTest(this, trackable.getId(), 100);

        getDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DistanceCalculationTask().execute("-37.807425,144.963814", routesInfo.get(0).getLocation());
            }
        });


        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkMapPermission();
        updateView();
    }

    private void updateView() {
        nameView.setText(trackable.getName());
        categoryView.setText(trackable.getCategory());
        descriptionView.setText(trackable.getDescription());
        urlView.setText(trackable.getUrl());
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.foodtruck, null));
    }

    private void showMap() {

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            @SuppressLint("MissingPermission")
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMyLocationEnabled(true);

                int stopsN = 1;
                LatLng location = null;

                // Asumes all data is correct, given that the file comes from us.
                for(RouteInfo routesInfo : routesInfo) {
                    // For dropping a marker at a point on the Map
                    Double lat = Double.valueOf(routesInfo.getLocation().split(",")[0]);
                    Double lng = Double.valueOf(routesInfo.getLocation().split(",")[1]);
                    location = new LatLng(lat, lng);

                    googleMap.addMarker(new MarkerOptions().position(location).title("Stop # " + stopsN));
                    stopsN++;
                }


                // For zooming automatically to the location of the last marker
                if(location != null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

            }
        });
    }

    private void checkMapPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            showMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMap();
                }
                return;
            }
        }
    }

}
