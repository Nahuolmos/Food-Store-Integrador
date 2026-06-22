package UI;

import entities.Categoria;
import Exceptions.EntityNotFoundException;
import exceptions.DuplicateEntityException;
import exceptions.ValidationException;
import service.CategoriaService;
import service.ProductoService;

import java.util.List;

public class CategoriaMenu {

    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final ConsolaHelper consola;

    public CategoriaMenu(CategoriaService categoriaService, ProductoService productoService, ConsolaHelper consola) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.consola = consola;
    }

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Categorías ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            switch (consola.leerEntero("Seleccione: ")) {
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> editar();
                case 4 -> eliminar();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public void listar() {
        List<Categoria> categorias = categoriaService.listar();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }
        categorias.forEach(System.out::println);
    }

    private void crear() {
        try {
            String nombre = consola.leerTexto("Nombre: ");
            String descripcion = consola.leerTexto("Descripción: ");
            Categoria creada = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada con id " + creada.getId());
        } catch (ValidationException | DuplicateEntityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        listar();
        Long id = consola.leerLong("Ingrese el id de la categoría a editar: ");
        try {
            String nombre = consola.leerTexto("Nuevo nombre (vacío para mantener actual): ", true);
            String descripcion = consola.leerTexto("Nueva descripción (vacío para mantener actual): ", true);
            categoriaService.editar(id, nombre, descripcion);
            System.out.println("Categoría actualizada correctamente.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        listar();
        Long id = consola.leerLong("Ingrese el id de la categoría a eliminar: ");
        if (!consola.confirmar("¿Confirma la eliminación de la categoría " + id + "? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            if (!productoService.listarPorCategoria(id).isEmpty()) {
                System.out.println("Aviso: la categoría tiene productos asociados. Se eliminará igualmente.");
            }
            categoriaService.eliminar(id);
            System.out.println("Categoría eliminada (baja lógica).");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
