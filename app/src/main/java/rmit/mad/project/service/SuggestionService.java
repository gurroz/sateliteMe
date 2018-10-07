package rmit.mad.project.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rmit.mad.project.dto.DistanceResponseDTO;
import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.view.TrackingSuggestionActivity;

public class SuggestionService extends IntentService {

    public static final int SUGGESTION_REQ_CODE = 15;

    public SuggestionService() {
        super("SuggestionService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("SuggestionService", "Running");
        if(isOnline(getApplicationContext())) {
            getSuggestion();
        }
    }

    public void getSuggestion() {
        DistanceCalculationService distanceService = new DistanceCalculationService();

        List<RouteInfo> routesInfo = RouteInfoService.getInstance().getTrackableRouteInfoFromNowStopping(this, 100);
        if(routesInfo.size() > 0) {
            Log.d("RouteInfoSaving", "Meetings time:" + routesInfo.size());

            List<String> destinations = new ArrayList();
            for(RouteInfo routeInfo : routesInfo) {
                Log.d("RouteInfoSaving", "Meetings time:" + routeInfo.toString());
                destinations.add(routeInfo.getLocation());
            }

            //TODO: Add actual position
            DistanceResponseDTO distanceResults = distanceService.getDistanceFromSource( "-37.807425,144.963814", destinations);

            if(distanceResults != null) {
                Log.d("SuggestionService", "Result es: " + distanceResults.toString());
                RouteInfoService.getInstance().saveSuggestedRoutesInfo(filterByArrivalTime(routesInfo, distanceResults));
                Intent suggestionIntent = new Intent(getBaseContext(), TrackingSuggestionActivity.class);
                getApplication().startActivity(suggestionIntent);
            }
        }


        stopSelf();
    }


    /**
     * Filter the list of potential suggestion based on the arrival time on the location will match the actual trackable
     * @param routesInfo List of potential meetings
     * @param distances information with the walking distance in time
     * @return
     */
    private List<RouteInfo> filterByArrivalTime(List<RouteInfo> routesInfo, DistanceResponseDTO distances) {
        List<DistanceResponseDTO.DestinationDistanceDTO> destinations = distances.getDestinations();
        List<RouteInfo> response = new ArrayList<>();
        for(int i = 0; i < routesInfo.size(); i++) {
            RouteInfo routeInfo = routesInfo.get(i);
            DistanceResponseDTO.DestinationDistanceDTO destinationDistance = destinations.get(i); // Asumes same amount of destinations as passed

            Calendar cal = Calendar.getInstance();
            int secondsAmount =  cal.get(Calendar.SECOND) + destinationDistance.getSecondsDistance();
            cal.set(Calendar.SECOND, secondsAmount);

            if(cal.getTime().before(routeInfo.getEndDateValue())) {
                Log.d("SuggestionService", "Aceptado-Meet: " + cal.getTime().toString() + " endDate: " + routeInfo.getEndDateValue().toString());
                response.add(fillRouteInfoMissingInfo(routeInfo, destinationDistance));
            }
        }

        return response;
    }

    private RouteInfo fillRouteInfoMissingInfo(RouteInfo routeInfo, DistanceResponseDTO.DestinationDistanceDTO destination) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + destination.getSecondsDistance());
        Trackable trackable = TrackableService.getInstance().getById(routeInfo.getTrackableId());

        routeInfo.fillMissingInfo(cal.getTime(), destination.getDestination(), trackable.getName(),
                trackable.getCategory(), destination.getTimeDistance());

        return routeInfo;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
}
