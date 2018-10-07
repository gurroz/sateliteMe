package rmit.mad.project.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import rmit.mad.project.service.SuggestionService;

public class LocationChangeDetector extends BroadcastReceiver {

    public final static String LOCATION_COORD = "LOCATION_COORD";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle= intent.getExtras();
        Location location = (Location)bundle.get(android.location.LocationManager.KEY_LOCATION_CHANGED);
        if(location != null) {
            String latlong = location.getLatitude() + "," + location.getLongitude();
            Intent suggestionIntent = new Intent(context, SuggestionService.class);
            suggestionIntent.putExtra(LOCATION_COORD, latlong);

            context.startService(suggestionIntent);
        }
    }
}
