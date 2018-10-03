package rmit.mad.project.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import rmit.mad.project.service.TrackableService;

import static rmit.mad.project.enums.IntentModelEnum.ROUTE_INFO;
import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private FragmentTabHost tabHost;

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

        initData();
    }

    /**
     * This method is only used to populate the app on first run. Should change when persistance is introduced.
     */
    private void initData() {
        Resources res = getResources();
        InputStream stream = res.openRawResource(R.raw.food_truck_data);

        try {
            TrackableService.getInstance().initTrackables(stream);
        } catch (IOException e) {
            Log.e(TAG, "Error reading food truck files: {}", e);
        }
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
}
