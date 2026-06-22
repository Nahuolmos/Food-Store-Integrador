package DAO;

import java.util.List;

public interface IBaseDAO<T> {
    T save(T entity);
    void update(T entity);
    T findById(Long id);
    List<T> findAll();
    void delete(Long id);
}
