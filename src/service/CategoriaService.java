package service;

import DAO.CategoriaDAO;
import Exceptions.EntityNotFoundException;
import entities.Categoria;
import exceptions.DuplicateEntityException;
import exceptions.ValidationException;
import java.util.List;

public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public CategoriaService(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public Categoria crear(String nombre, String descripcion) throws ValidationException, DuplicateEntityException {
        validarNombre(nombre);
        if (categoriaDAO.existePorNombre(nombre)) {
            throw new DuplicateEntityException("Ya existe una categoria con el nombre '" + nombre + "'");
        }
        Categoria categoria = new Categoria(nombre, descripcion);
        return categoriaDAO.crear(categoria);
    }

    public List<Categoria> listar() {
        return categoriaDAO.listarTodos();
    }

    public Categoria buscarPorId(Long id) throws EntityNotFoundException {
        return categoriaDAO.buscarPorId(id);
    }

    public void editar(Long id, String nombre, String descripcion)
            throws EntityNotFoundException, ValidationException, DuplicateEntityException {
        validarNombre(nombre);
        Categoria categoria = categoriaDAO.buscarPorId(id);
        if (!categoria.getNombre().equalsIgnoreCase(nombre) && categoriaDAO.existePorNombre(nombre)) {
            throw new DuplicateEntityException("Ya existe una categoria con el nombre '" + nombre + "'");
        }
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        categoriaDAO.actualizar(categoria);
    }

    public boolean tieneProductosAsociados(Long id) {
        return categoriaDAO.tieneProductosAsociados(id);
    }

    public void eliminar(Long id) throws EntityNotFoundException {
        categoriaDAO.buscarPorId(id);
        categoriaDAO.eliminar(id);
    }

    private void validarNombre(String nombre) throws ValidationException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre de la categoria no puede estar vacio");
        }
    }
}
