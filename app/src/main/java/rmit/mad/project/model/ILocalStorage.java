package rmit.mad.project.model;

import java.util.List;

public interface ILocalStorage<E> {

    List<E> getAll();
    E getById(String id);
    void save(String id, E e);
    void delete(String id);
}
