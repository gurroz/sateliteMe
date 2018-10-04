package rmit.mad.project.dto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DistanceResponseDTO {
    private String origin;
    private List<DestinationDistanceDTO> destinations;

    public DistanceResponseDTO(JSONObject json) throws JSONException {
        origin = json.getString("origin_addresses");
        destinations = new ArrayList<>();

        JSONArray destArr = json.getJSONArray("destination_addresses");
        JSONArray rowsArr = json.getJSONArray("rows");
        for(int i= 0; i < destArr.length(); i++) {
            if (rowsArr.length() > 0) {
                JSONArray elements = ((JSONObject) rowsArr.get(0)).getJSONArray("elements");
                if (elements.length() > 0) {
                    String location = (String) destArr.get(i);
                    String timeDistance = ((JSONObject) elements.get(i)).getJSONObject("duration").getString("text");
                    int secondsDistance = ((JSONObject) elements.get(i)).getJSONObject("duration").getInt("value");

                    DestinationDistanceDTO destination = new DestinationDistanceDTO(location, timeDistance, secondsDistance);
                    destinations.add(destination);
                }
            }
        }
    }

    public String getOrigin() {
        return origin;
    }

    public List<DestinationDistanceDTO> getDestinations() {
        return destinations;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DistanceResponseDTO{");
        sb.append("origin='").append(origin).append('\'');
        sb.append(", destinations=").append(destinations);
        sb.append('}');
        return sb.toString();
    }

    public class DestinationDistanceDTO {
        private String destination;
        private String timeDistance;
        private int secondsDistance;

        public DestinationDistanceDTO(String destination, String timeDistance, int secondsDistance) {
            this.destination = destination;
            this.timeDistance = timeDistance;
            this.secondsDistance = secondsDistance;
        }

        public String getDestination() {
            return destination;
        }

        public String getTimeDistance() {
            return timeDistance;
        }

        public int getSecondsDistance() {
            return secondsDistance;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("DestinationDistanceDTO{");
            sb.append("destination='").append(destination).append('\'');
            sb.append(", timeDistance='").append(timeDistance).append('\'');
            sb.append(", secondsDistance=").append(secondsDistance);
            sb.append('}');
            return sb.toString();
        }
    }
}