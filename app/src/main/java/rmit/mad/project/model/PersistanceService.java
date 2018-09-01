package rmit.mad.project.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PersistanceService<E> implements IPersistanceService<E>  {

    protected Map<String, E> collectionMap = new HashMap<String, E>();

    @Override
    public List<E> getAll() {
        return new ArrayList<E>(collectionMap.values());
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
}
