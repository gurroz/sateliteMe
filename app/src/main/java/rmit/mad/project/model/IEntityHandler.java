package rmit.mad.project.model;

import java.util.List;

public interface IEntityHandler<E> {

    List<E> getAll();
    void saveAll(List<E> entities);
    E getById(String id);
    void save(String id, E e);
    void delete(String id);
    void setDatabaseHandler(DatabaseHandler db);
    void persistToDB();
}
