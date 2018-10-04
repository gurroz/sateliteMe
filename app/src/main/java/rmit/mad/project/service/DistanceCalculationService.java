package rmit.mad.project.service;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import rmit.mad.project.dto.DistanceResponseDTO;

public class DistanceCalculationService {

    private  String API_KEY = "AIzaSyDw9DNZxB0XgnSRnAK_IgZKmTdjwLp76WI";
    private  String DISTANCE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=walking&units=imperial&key=" + API_KEY;


    public DistanceCalculationService() {}

    /**
     *  Uses a web service to calculate the walking distance in time from the origin to the destination
     * @param origin
     * @param destination
     * @return
     */
    protected DistanceResponseDTO getDistanceFromSource(String origin, List<String> destination) {
        HttpClient httpclient = new DefaultHttpClient();

        String distanceURL = DISTANCE_URL + "&origins=" + URLEncoder.encode(origin) + "&destinations=";

        for(int i = 0; i < destination.size(); i++) {
            distanceURL += URLEncoder.encode("|" + destination.get(i));
        }

        Log.e("DistanceCalculationTask", distanceURL);
        HttpGet getRequest = new HttpGet(distanceURL);

        DistanceResponseDTO response = null;

        try {
            String responseBody = httpclient.execute(getRequest, new BasicResponseHandler());
            JSONObject objJson = new JSONObject(responseBody);
            response = new DistanceResponseDTO(objJson);

            Log.d("DISTANCEOBJ", response.toString());
        } catch (IOException|JSONException e) {
            Log.e("DistanceCalculationTask", e.getMessage());
        }

        return response;
    }

}
