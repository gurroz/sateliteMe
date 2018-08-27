package rmit.mad.project.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TrackableService {


    private TrackableService() { }

    public static TrackableService getInstance() {
        return LazyHolder.instance;
    }

    public void initTrackables(InputStream textFile) throws IOException {
        List<Trackable> trackables = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(textFile));
        String line = reader.readLine();

        while (line != null) {
            String[] properties = line.split(",");

            Trackable truk = new TrackableImp();
            truk.setId(properties[0]);
            truk.setName(properties[1].replaceAll("\"", ""));
            truk.setDescription(properties[2].replaceAll("\"", ""));
            truk.setUrl(properties[3].replaceAll("\"", ""));
            truk.setCategory(properties[4].replaceAll("\"", ""));


            TrackableDAO.getInstance().persistTrackable(truk);

            trackables.add(truk);
            line = reader.readLine();
        }
    }

    public List<Trackable> getTrackables() {
        return TrackableDAO.getInstance().getTrackables();
    }

    private static class LazyHolder {
        static final TrackableService instance = new TrackableService();
    }
}
