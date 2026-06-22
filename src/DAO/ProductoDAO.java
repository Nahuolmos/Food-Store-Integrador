package DAO;

import entities.Producto;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO en memoria para Producto. Hereda el CRUD genérico de GenericMemoryDAO.
 */
public class ProductoDAO extends GenericMemoryDAO<Producto> {

    public List<Producto> findByCategoria(Long categoriaId) {
        return findAll().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId))
                .collect(Collectors.toList());
    }

    public boolean tieneProductosAsociados(Long categoriaId) {
        return !findByCategoria(categoriaId).isEmpty();
    }
}
