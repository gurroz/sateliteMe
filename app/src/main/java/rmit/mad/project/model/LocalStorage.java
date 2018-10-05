package rmit.mad.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LocalStorage<E> implements ILocalStorage<E> {

    protected Map<String, E> collectionMap = new HashMap<String, E>();

    @Override
    public List<E> getAll() {
        if(collectionMap.isEmpty()) {
            List<E> data = this.getFromDatabase();
            for(E obj: data) {
                collectionMap.put(getIdFromObject(obj), obj);
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

    public abstract void persistDatabase();
    public abstract List<E> getFromDatabase();
    public abstract List<E> sevaToDatabase(String id, E e);
    public abstract String getIdFromObject(Object e);

}
