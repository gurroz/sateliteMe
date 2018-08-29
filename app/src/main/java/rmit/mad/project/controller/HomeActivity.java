package rmit.mad.project.controller;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.TrackableService;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;
import rmit.mad.project.model.TrackingImp;
import rmit.mad.project.model.TrackingService;

public class HomeActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackables);

        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


        String tabOneName = getString(R.string.tabs_option1);
        String tabTwoName = getString(R.string.tabs_option2);

        TabHost.TabSpec tab = tabHost.newTabSpec(tabOneName);
        tab.setIndicator(tabOneName);

        tabHost.addTab(tab, TrackableListActivity.class,null);

        tab = tabHost.newTabSpec(tabTwoName);
        tab.setIndicator(tabTwoName);

        tabHost.addTab(tab, TrackingListActivity.class,null);

        Resources res = getResources();
        InputStream stream = res.openRawResource(R.raw.food_truck_data);

        try {
           TrackableService.getInstance().initTrackables(stream);

            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            String searchDate = "05/07/2018 1:00:00 PM";
            int searchWindow = 120; // +/- 5 mins
            Date date = dateFormat.parse(searchDate);

            List<TrackingService.TrackingInfo> trackingsInfo = TrackingService.getSingletonInstance(this).getTrackingInfoForTimeRange(date, searchWindow, 0 );
            Log.d("Assigment", "Info are: " + trackingsInfo.size());

            int i = 1;
            for(TrackingService.TrackingInfo info : trackingsInfo) {
                Tracking tracking = new TrackingImp();
                tracking.setTargetStartTime(info.date);

                Calendar targetCalStart = Calendar.getInstance();
                targetCalStart.setTime(info.date);
                targetCalStart.set(Calendar.MINUTE, targetCalStart.get(Calendar.MINUTE) + info.stopTime);
                tracking.setTargetFinishTime(targetCalStart.getTime());

                tracking.setActualLocation(info.latitude + ","+ info.longitude);
                tracking.setId(String.valueOf(i));//TODO: generate unique id
                i++;

                TrackingDAO.getInstance().persistTracking(tracking);
            }

        } catch (IOException e) {
            Toast.makeText(this,"Error readin food truck files", Toast.LENGTH_LONG);
            Log.d("ASSGIMENT", "Error readinf food truck", e);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ASSGIMENT", "Error creating date", e);
        }
    }
}
