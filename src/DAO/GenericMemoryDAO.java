package DAO;

import Exceptions.EntityNotFoundException;
import entities.Base;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación genérica en memoria (Map en vez de tabla SQL).
 * Centraliza la generación de ids y la lógica de baja lógica (soft delete).
 */
public abstract class GenericMemoryDAO<T extends Base> implements IBaseDAO<T> {

    protected final Map<Long, T> tablaSimulada = new LinkedHashMap<>();
    protected final AtomicLong secuenciaId = new AtomicLong(1);

    @Override
    public T save(T entity) {
        Long nuevoId = secuenciaId.getAndIncrement();
        entity.setId(nuevoId);
        entity.setEliminado(false);
        tablaSimulada.put(nuevoId, entity);
        return entity;
    }

    @Override
    public void update(T entity) {
        if (entity.getId() == null || !tablaSimulada.containsKey(entity.getId())) {
            try {
                throw new EntityNotFoundException("No existe el registro con id " + entity.getId());
            } catch (EntityNotFoundException ex) {
                System.getLogger(GenericMemoryDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        tablaSimulada.put(entity.getId(), entity);
    }

    @Override
    public Optional<T> findById(Long id) {
        T entity = tablaSimulada.get(id);
        if (entity == null || entity.isEliminado()) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public List<T> findAll() {
        return tablaSimulada.values().stream()
                .filter(e -> !e.isEliminado())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        T entity = tablaSimulada.get(id);
        if (entity == null || entity.isEliminado()) {
            try {
                throw new EntityNotFoundException("No existe el registro con id " + id);
            } catch (EntityNotFoundException ex) {
                System.getLogger(GenericMemoryDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        entity.setEliminado(true);
    }
}
