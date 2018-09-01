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
            String[] properties = line.split(",\"");

            Trackable truk = new TrackableImp();
            truk.setId(Integer.valueOf(properties[0]));
            truk.setName(properties[1].replaceAll("\"", ""));
            truk.setDescription(properties[2].replaceAll("\"", ""));
            truk.setUrl(properties[3].replaceAll("\"", ""));
            truk.setCategory(properties[4].replaceAll("\"", ""));


            TrackableDAO.getInstance().save(String.valueOf(truk.getId()), truk);

            trackables.add(truk);
            line = reader.readLine();
        }
    }

    public List<Trackable> getTrackables() {
        return TrackableDAO.getInstance().getAll();
    }

    public List<Trackable> getTrackablesFilteredByCategory(List<String> categories) {
        List<Trackable> filteredTrackables = new ArrayList<>();
        List<Trackable> allTrackables = TrackableDAO.getInstance().getAll();
        for(Trackable trackable : allTrackables) {
            if(categories.contains(trackable.getCategory())) {
                filteredTrackables.add(trackable);
            }
        }
        return filteredTrackables;
    }

    public String[] getTrackablesCategories() {
        List<String> categories = new ArrayList<>();
        List<Trackable> allTrackables = TrackableDAO.getInstance().getAll();
        for(Trackable trackable : allTrackables) {
            if(!categories.contains(trackable.getCategory())) {
                categories.add(trackable.getCategory());
            }
        }
        return categories.toArray(new String[categories.size()]);
    }

    private static class LazyHolder {
        static final TrackableService instance = new TrackableService();
    }
}
