package rmit.mad.project.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class LocalStorage<E> implements IEntityHandler<E> {

    protected Map<String, E> collectionMap = new HashMap<String, E>();
    protected DatabaseHandler dbInstance;

    protected LocalStorage() { }

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

    @Override
    public void saveAll(List<E> entities) {
        for(E entity: entities) {
            save( getId(entity), entity);
        }
    }

    @Override
    public void setDatabaseHandler(DatabaseHandler db) {
        this.dbInstance = db;
    }


    protected abstract String getId(E entity);

}
