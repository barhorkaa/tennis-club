package cz.hornakova.barbora.tennisclub.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> getAll();
    Optional<T> getById(Long id);

    T save(T t);
    void update(T t);
    void delete(T t);
}
