package rmit.mad.project.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rmit.mad.project.dto.DistanceResponseDTO;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.view.TrackingSuggestionActivity;

public class SuggestionService extends IntentService {

    public SuggestionService() {
        super("SuggestionService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("SuggestionService", "Running");
        getSuggestion();

    }

    public void getSuggestion() {
        List<Trackable> trackables = TrackableService.getInstance().getTrackablesList();
        DistanceCalculationService distanceService = new DistanceCalculationService();

        List<RouteInfo> routesInfo = null;
        for(Trackable trackable: trackables) {
            routesInfo = RouteInfoService.getInstance().getTrackableRouteInfoTest(this, trackable.getId(), 100);
            if(routesInfo != null && routesInfo.size() > 0) {
                Log.d("SuggestionService", "RouteInfo No es nulo: " + trackable.toString());
                break;
            }
        }

        Log.d("SuggestionService", "RouteInfo es: " + routesInfo.toString());
        List<String> destinations = new ArrayList();
        for(RouteInfo routeInfo : routesInfo) {
            destinations.add(routeInfo.getLocation());
        }

        DistanceResponseDTO result = distanceService.getDistanceFromSource( "-37.807425,144.963814", destinations);
        Log.d("SuggestionService", "Result es: " + result.toString());

        Intent suggestionIntent = new Intent(getBaseContext(), TrackingSuggestionActivity.class);
        getApplication().startActivity(suggestionIntent);

        stopSelf();
    }


    private void filterByLeavingTime() {

    }



}
