package rmit.mad.project.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        DistanceCalculationService distanceService = new DistanceCalculationService();

        List<RouteInfo> routesInfo = RouteInfoService.getInstance().getTrackableRouteInfoFromNowStopping(this, 100);
        List<String> destinations = new ArrayList();
        for(RouteInfo routeInfo : routesInfo) {
            destinations.add(routeInfo.getLocation());
        }

        DistanceResponseDTO distanceResults = distanceService.getDistanceFromSource( "-37.807425,144.963814", destinations);

        if(distanceResults != null) {
            Log.d("SuggestionService", "Result es: " + distanceResults.toString());
            RouteInfoService.getInstance().saveSuggestedRoutesInfo(filterByArrivalTime(routesInfo, distanceResults));
            Intent suggestionIntent = new Intent(getBaseContext(), TrackingSuggestionActivity.class);
            getApplication().startActivity(suggestionIntent);
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
            cal.set(Calendar.SECOND, cal.get(Calendar.MINUTE) + destinationDistance.getSecondsDistance());

            // TODO: This has to be added when testing when real data
//            if(cal.getTime().before(routeInfo.getEndDateValue())) {
//                response.add(fillRouteInfohMissingInfo(routeInfo, destinationDistance));
//            }

            response.add(fillRouteInfohMissingInfo(routeInfo, destinationDistance));

        }

        return response;
    }

    private RouteInfo fillRouteInfohMissingInfo(RouteInfo routeInfo, DistanceResponseDTO.DestinationDistanceDTO destination) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.SECOND, cal.get(Calendar.MINUTE) + destination.getSecondsDistance());

        Trackable trackable = TrackableService.getInstance().getById(routeInfo.getTrackableId());

        routeInfo.fillMissingInfo(cal.getTime(), destination.getDestination(), trackable.getName(),
                trackable.getCategory(), destination.getTimeDistance());

        return routeInfo;
    }

}
