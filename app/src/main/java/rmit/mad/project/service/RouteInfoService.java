package rmit.mad.project.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Observable;

import rmit.mad.project.model.RouteInfo;
import rmit.mad.project.model.RouteInfoDAO;

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
        Log.d("TAG", "Assquing "+ trackableId + "for date: " + actualDate.toString());
        int amount = 0;
        for(TrackingService.TrackingInfo routeInfo : routesInfo) {
            Log.d("TAG", "REspond Tracking info: " + routeInfo.toString());

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

    public List<RouteInfo> getTrackableRouteInfoFromNowStopping(Context context, int limit) {
        Date actualDate = new Date();
        int searchWindow = 1440;

        List<TrackingService.TrackingInfo> routesInfo = TrackingService.getSingletonInstance(context).getTrackingInfoForTimeRange(actualDate, searchWindow, 0 );
        Log.d("TAG", "TrackingInfo: " + routesInfo.size());


        List<RouteInfo> routesInfoFiltered = new ArrayList<>();
        int amount = 0;

        for(TrackingService.TrackingInfo routeInfo : routesInfo) {
            Log.d("TAG", "REspond Tracking Stopping info: " + routeInfo.toString());

            if(routeInfo.stopTime > 0) {
                routesInfoFiltered.add(new RouteInfo(routeInfo.trackableId, routeInfo.date, routeInfo.stopTime, routeInfo.latitude, routeInfo.longitude));
                amount++;
            }

            if(amount == limit) {
                break;
            }
        }

        return routesInfoFiltered;
    }


    public RouteInfo getSuggestedRoutesInfo() {
        RouteInfoDAO.getInstance().toString();
        RouteInfo response = null;
        try {
            response =  RouteInfoDAO.getInstance().get();
        } catch (EmptyStackException e) {

        }
        return response;
    }

    public void saveSuggestedRoutesInfo(List<RouteInfo> routesInfo) {
        for (RouteInfo routeInfo : routesInfo) {
            RouteInfoDAO.getInstance().insert(routeInfo);
        }
    }

    public void clearSuggestedRoutesInfo() {
        RouteInfoDAO.getInstance().clear();
    }

}
