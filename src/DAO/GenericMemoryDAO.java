package DAO;

import entities.Base;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Motor Simulado en Memoria que replica las interacciones DAO con auto-incremento de ID.
 */
public class GenericMemoryDAO<T extends Base> implements IBaseDAO<T> {
    protected Map<Long, T> storage = new HashMap<>();
    private long currentId = 1;

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        for (T entity : storage.values()) {
            if (!entity.isEliminado()) {
                list.add(entity);
            }
        }
        return list;
    }

    @Override
    public void update(T entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) {
        T entity = findById(id);
        if (entity != null) {
            entity.setEliminado(true); // Soft Delete estricto
        }
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(currentId++);
        }
         return storage.put(entity.getId(), entity);   
    }

    @Override
    public T findById(Long id) {
        return storage.get(id);
    }

}