package rmit.mad.project.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import rmit.mad.project.model.FoodTruck;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableDAO;

public class TrackableService extends Observable {

    private static class LazyHolder {
        static final TrackableService instance = new TrackableService();
    }

    private TrackableService() {
    }

    public static TrackableService getInstance() {
        return LazyHolder.instance;
    }

    public void initTrackables(InputStream textFile) throws IOException {
        // Check if database is full if not, create
        if(!TrackableDAO.getInstance().isDBInitiated()) {
            List<Trackable> trackables = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(textFile));
            String line = reader.readLine();

            while (line != null) {
                String[] properties = line.split(",\"");

                Trackable truk = new FoodTruck();
                truk.setId(properties[0]);
                truk.setName(properties[1].replaceAll("\"", ""));
                truk.setDescription(properties[2].replaceAll("\"", ""));
                truk.setUrl(properties[3].replaceAll("\"", ""));
                truk.setCategory(properties[4].replaceAll("\"", ""));


                TrackableDAO.getInstance().save(String.valueOf(truk.getId()), truk);

                trackables.add(truk);
                line = reader.readLine();
            }
        }

    }

    public void getTrackables() {
        setChanged();
        notifyObservers(TrackableDAO.getInstance().getAll());
    }

    public List<Trackable> getTrackablesList() {
        return TrackableDAO.getInstance().getAll();
    }

    public void getTrackablesFilteredByCategory(List<String> categories) {
        List<Trackable> filteredTrackables = new ArrayList<>();
        List<Trackable> allTrackables = TrackableDAO.getInstance().getAll();
        for (Trackable trackable : allTrackables) {
            if (categories.contains(trackable.getCategory())) {
                filteredTrackables.add(trackable);
            }
        }

        setChanged();
        notifyObservers(filteredTrackables);
    }

    public String[] getTrackablesCategories() {
        List<String> categories = new ArrayList<>();
        List<Trackable> allTrackables = TrackableDAO.getInstance().getAll();
        for (Trackable trackable : allTrackables) {
            if (!categories.contains(trackable.getCategory())) {
                categories.add(trackable.getCategory());
            }
        }
        return categories.toArray(new String[categories.size()]);
    }

    public Trackable getById(String id) {
        return TrackableDAO.getInstance().getById(id);
    }


}