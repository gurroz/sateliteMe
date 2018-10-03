package rmit.mad.project.service;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URLEncoder;

public class DistanceCalculationTask extends AsyncTask<String, Void, String> {

    private  String API_KEY = "AIzaSyDw9DNZxB0XgnSRnAK_IgZKmTdjwLp76WI";
    private  String DISTANCE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=walking&units=imperial&key=" + API_KEY;

    /**
     *  Uses a web service to calculate the walking distance in time from the origin to the destination
     * @param originDestination array of Lat and long in the following format: [(41.43206,-81.38992), (41.43206,-81.38992)]
     * @return Time of walking distance between oring and destination
     */
    @Override
    protected String doInBackground(String... originDestination) {
        HttpClient httpclient = new DefaultHttpClient();

        String distanceURL = DISTANCE_URL + "&origins=" + URLEncoder.encode(originDestination[0]) + "&destinations=" + URLEncoder.encode(originDestination[1]);
        Log.e("DistanceCalculationTask", distanceURL);

        HttpGet getRequest = new HttpGet(distanceURL);

        String responseBody = "";

        try {
            responseBody = httpclient.execute(getRequest, new BasicResponseHandler());
        } catch (IOException e) {
            Log.e("DistanceCalculationTask", e.getMessage());
        }

        return responseBody;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        Log.d("DISTANCE", result);
    }
}
