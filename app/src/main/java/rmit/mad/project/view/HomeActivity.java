package rmit.mad.project.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import java.io.IOException;
import java.io.InputStream;

import rmit.mad.project.R;
import rmit.mad.project.receiver.NetworkChangeDetector;
import rmit.mad.project.service.AlarmService;
import rmit.mad.project.service.TrackableService;
import rmit.mad.project.service.TrackableTrackingsService;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private static final int REQUEST_CODE = 100;

    private FragmentTabHost tabHost;
    private BroadcastReceiver networkBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

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
        checkLocationPermission();

        initAlarms();
        initData();
        initReceivers();
    }

    private void initReceivers() {
        Log.d(TAG, "Starting Receivers");
        final IntentFilter networkIntent = new IntentFilter();
        networkIntent.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);

        networkBroadcastReceiver = new NetworkChangeDetector();
        registerReceiver(networkBroadcastReceiver, networkIntent);
    }

    private void initAlarms() {
        Log.d(TAG, "Starting Alarms");
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        AlarmService.setAlarmSuggestions(this);
        AlarmService.setMeetingNotification(this);
    }

    /**
     * This method is only used to populate the app on first run. Should change when persistance is introduced.
     */
    private void initData() {
        Resources res = getResources();
        InputStream stream = res.openRawResource(R.raw.food_truck_data);

        try {
            TrackableTrackingsService.getInstance().init(this);
            TrackableService.getInstance().initTrackables(stream, this);
        } catch (IOException e) {
            Log.e(TAG, "Error reading food truck files: {}", e);
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(networkBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trackables, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permissions accepted");
                }  else {
                    Log.d(TAG, "Permissions NOT accepted");
                }
                return;
            }
        }
    }
}
