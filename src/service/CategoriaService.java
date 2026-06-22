package service;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import entities.Categoria;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO;
    private ProductoDAO productoDAO;

    public CategoriaService(CategoriaDAO categoriaDAO, ProductoDAO productoDAO) {
        this.categoriaDAO = categoriaDAO;
        this.productoDAO = productoDAO;
    }

    public void crearCategoria(String nombre, String descripcion) throws ValidationException, DuplicateEntityException {
        if (nombre == null || nombre.trim().isEmpty()) throw new ValidationException("El nombre no puede estar vacío.");
        if (descripcion == null || descripcion.trim().isEmpty()) throw new ValidationException("La descripción no puede estar vacía.");
        if (categoriaDAO.findByNombre(nombre) != null) throw new DuplicateEntityException("Ya existe una categoría con ese nombre.");

        Categoria cat = new Categoria(nombre, descripcion);
        categoriaDAO.save(cat);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.findAll();
    }

    public Categoria obtenerPorId(Long id) throws EntityNotFoundException {
        Categoria c = categoriaDAO.findById(id);
        if (c == null || c.isEliminado()) throw new EntityNotFoundException("La categoría con ID " + id + " no existe.");
        return c;
    }

    public void editarCategoria(Long id, String nombre, String descripcion) throws EntityNotFoundException, ValidationException {
        Categoria c = obtenerPorId(id);
        if (nombre == null || nombre.trim().isEmpty()) throw new ValidationException("El nombre no puede estar vacío.");
        if (descripcion == null || descripcion.trim().isEmpty()) throw new ValidationException("La descripción no puede estar vacía.");

        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        categoriaDAO.update(c);
    }

    public void eliminarCategoria(Long id) throws EntityNotFoundException, ValidationException {
        obtenerPorId(id);
        if (productoDAO.tieneProductosAsociados(id)) {
            throw new ValidationException("No se puede eliminar la categoría porque contiene productos activos.");
        }
        categoriaDAO.delete(id);
    }
}
