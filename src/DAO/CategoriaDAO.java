package DAO;

import entities.Categoria;

public class CategoriaDAO extends GenericMemoryDAO<Categoria> {
    public Categoria findByNombre(String nombre) {
        for (Categoria c : storage.values()) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }
}