package rmit.mad.project.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import rmit.mad.project.model.RouteInfo;

public class RouteInfoService extends Observable {

    private static class LazyHolder {
        static final RouteInfoService instance = new RouteInfoService();
    }

    private RouteInfoService() { }

    public static RouteInfoService getInstance() {
        return LazyHolder.instance;
    }

    public List<RouteInfo> getTrackableRouteInfoFromNow(Context context, String trackableId, int limit) {
        Date actualDate = new Date();
        int searchWindow = 1440;

        List<TrackingService.TrackingInfo> routesInfo = TrackingService.getSingletonInstance(context).getTrackingInfoForTimeRange(actualDate, searchWindow, 0 );
        List<RouteInfo> routesInfoFiltered = new ArrayList<>();

        int amount = 0;
        for(TrackingService.TrackingInfo routeInfo : routesInfo) {
            if(Integer.valueOf(trackableId) == routeInfo.trackableId) {
                routesInfoFiltered.add(new RouteInfo(routeInfo.trackableId, routeInfo.date, routeInfo.stopTime, routeInfo.latitude, routeInfo.longitude));
                amount++;
            }

            if(amount == limit) {
                break;
            }
        }

        return routesInfoFiltered;
    }

}
