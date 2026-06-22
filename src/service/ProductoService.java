package service;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import entities.Categoria;
import entities.Producto;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import java.util.List;

public class ProductoService {

    private final ProductoDAO productoDAO;
    private final CategoriaDAO categoriaDAO;

    public ProductoService(ProductoDAO productoDAO, CategoriaDAO categoriaDAO) {
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
    }

    public Producto crear(String nombre, String descripcion, Double precio, int stock, String imagen, Long categoriaId)
            throws ValidationException, EntityNotFoundException {
        validarDatos(nombre, precio, stock);
        Categoria categoria = categoriaDAO.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría seleccionada no existe."));

        Producto producto = new Producto(nombre, precio, descripcion, stock, imagen, stock > 0, categoria);
        producto.setCategoria(categoria);
        return productoDAO.save(producto);
    }

    public List<Producto> listarTodos() {
        return productoDAO.findAll();
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoDAO.findByCategoria(categoriaId);
    }

    public Producto buscarPorId(Long id) throws EntityNotFoundException {
        return productoDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID " + id + " no existe."));
    }

    public void actualizar(Long id, String nombre, String descripcion, Double precio, int stock, String imagen, Long categoriaId)
            throws EntityNotFoundException, ValidationException {
        Producto producto = buscarPorId(id);
        validarDatos(nombre, precio, stock);
        Categoria categoria = categoriaDAO.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("La categoría seleccionada no existe."));

        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setDisponible(stock > 0);
        producto.setImagen(imagen);
        producto.setCategoria(categoria);
        productoDAO.update(producto);
    }

    public void eliminar(Long id) throws EntityNotFoundException {
        buscarPorId(id);
        productoDAO.delete(id);
    }

    private void validarDatos(String nombre, Double precio, int stock) throws ValidationException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre no puede estar vacío.");
        }
        if (precio == null || precio < 0) {
            throw new ValidationException("El precio no puede ser menor a 0.");
        }
        if (stock < 0) {
            throw new ValidationException("El stock no puede ser menor a 0.");
        }
    }
}
