package service;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import entities.Categoria;
import exceptions.DuplicateEntityException;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import java.util.List;

public class CategoriaService {

    private final CategoriaDAO categoriaDAO;
    private final ProductoDAO productoDAO;

    public CategoriaService(CategoriaDAO categoriaDAO, ProductoDAO productoDAO) {
        this.categoriaDAO = categoriaDAO;
        this.productoDAO = productoDAO;
    }

    public Categoria crear(String nombre, String descripcion) throws ValidationException, DuplicateEntityException {
        validarDatos(nombre, descripcion);
        if (categoriaDAO.existePorNombre(nombre)) {
            throw new DuplicateEntityException("Ya existe una categoría con ese nombre.");
        }
        Categoria categoria = new Categoria(nombre, descripcion);
        return categoriaDAO.save(categoria);
    }

    public List<Categoria> listar() {
        return categoriaDAO.findAll();
    }

    public Categoria buscarPorId(Long id) throws EntityNotFoundException {
        return categoriaDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La categoría con ID " + id + " no existe."));
    }

    public void editar(Long id, String nombre, String descripcion) throws EntityNotFoundException, ValidationException, DuplicateEntityException {
        Categoria categoria = buscarPorId(id);
        validarDatos(nombre, descripcion);
        if (!categoria.getNombre().equalsIgnoreCase(nombre) && categoriaDAO.existePorNombre(nombre)) {
            throw new DuplicateEntityException("Ya existe una categoría con ese nombre.");
        }
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoriaDAO.update(categoria);
    }

    public void eliminar(Long id) throws EntityNotFoundException, ValidationException {
        buscarPorId(id);
        if (productoDAO.tieneProductosAsociados(id)) {
            throw new ValidationException("No se puede eliminar la categoría porque contiene productos activos.");
        }
        categoriaDAO.delete(id);
    }

    private void validarDatos(String nombre, String descripcion) throws ValidationException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre no puede estar vacío.");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new ValidationException("La descripción no puede estar vacía.");
        }
    }
}
