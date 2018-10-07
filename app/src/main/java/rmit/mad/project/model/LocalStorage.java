package rmit.mad.project.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rmit.mad.project.model.Database.DatabaseHelper;

public abstract class LocalStorage<E> implements ILocalStorage<E> {

    protected Map<String, E> collectionMap = new HashMap<String, E>();
    //DatabaseHelper dbh = DatabaseHelper.getSingletonInstance() context needed

    @Override
    public List<E> getAll() { //DatabaseHelper parameter needs to be added
        if(collectionMap.isEmpty()) {
            List<E> data = this.getFromDatabase(dbh);
            for(E obj: data) {
                collectionMap.put(getIdFromObject(dbh, obj), obj);
            }
            return data;
        } else {
            return new ArrayList<E>(collectionMap.values());
        }
    }

    @Override
    public E getById(String id) {
        return collectionMap.get(id);
    }

    @Override
    public void save(String id, E e) {
        collectionMap.put(id, e);
    }

    @Override
    public void delete(String id) {
        collectionMap.remove(id);
    }

//    public abstract void persistDatabase();
//    public abstract List<E> getFromDatabase();
//    public abstract List<E> saveToDatabase(String id, E e);
//    public abstract String getIdFromObject(Object e);

    //public abstract void persistDatabase (DatabaseHelper dbh, InputStream textFile);
    public abstract List<E> getFromDatabase (DatabaseHelper dbh);
    public abstract void addToDatabase (DatabaseHelper dbh, String id, E e);
    public abstract String getIdFromObject(DatabaseHelper dbh, E e);
    //public abstract boolean deleteTrackingFromBD (DatabaseHelper dbh, String trackingID);

}
