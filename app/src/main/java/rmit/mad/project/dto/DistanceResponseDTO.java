package rmit.mad.project.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DistanceResponseDTO {
    private String origin;
    private String destination;
    private String timeDistance;
    private int secondsDistance;

    public DistanceResponseDTO(JSONObject json) throws JSONException {
        origin = json.getString("origin_addresses");
        destination = json.getString("destination_addresses");
        if (json.getJSONArray("rows").length() > 0) {
            JSONArray elements = ((JSONObject) json.getJSONArray("rows").get(0)).getJSONArray("elements");
            if (elements.length() > 0) {
                timeDistance = ((JSONObject) elements.get(0)).getJSONObject("duration").getString("text");
                secondsDistance = ((JSONObject) elements.get(0)).getJSONObject("duration").getInt("value");
            }
        }
    }


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTimeDistance() {
        return timeDistance;
    }

    public void setTimeDistance(String timeDistance) {
        this.timeDistance = timeDistance;
    }

    public int getSecondsDistance() {
        return secondsDistance;
    }

    public void setSecondsDistance(int secondsDistance) {
        this.secondsDistance = secondsDistance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DistanceResponseDTO{");
        sb.append("origin='").append(origin).append('\'');
        sb.append(", destination='").append(destination).append('\'');
        sb.append(", timeDistance='").append(timeDistance).append('\'');
        sb.append(", secondsDistance=").append(secondsDistance);
        sb.append('}');
        return sb.toString();
    }

}