package DAO;

import java.util.List;
import java.util.Optional;

public interface IBaseDAO<T> {

    T save(T entity);

    void update(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void delete(Long id);
}
