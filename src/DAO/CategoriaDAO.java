package DAO;

import entities.Categoria;

/**
 * DAO en memoria para Categoria. Hereda el CRUD genérico (save, update,
 * findById, findAll, delete con baja lógica) de GenericMemoryDAO.
 */
public class CategoriaDAO extends GenericMemoryDAO<Categoria> {

    public boolean existePorNombre(String nombre) {
        return findAll().stream()
                .anyMatch(c -> c.getNombre().equalsIgnoreCase(nombre));
    }
}
