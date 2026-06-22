package UI;

import entities.Categoria;
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
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar Categorías");
            System.out.println("2. Crear Categoría");
            System.out.println("3. Editar Categoría");
            System.out.println("4. Eliminar Categoría");
            System.out.println("0. Volver");
            switch (consola.leerEntero("Seleccione una opción: ")) {
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
        List<Categoria> lista = categoriaService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crear() {
        String nombre = consola.leerTexto("Ingrese nombre de la categoría: ");
        String desc = consola.leerTexto("Ingrese descripción: ");
        try {
            Categoria nueva = categoriaService.crear(nombre, desc);
            System.out.println("¡Categoría creada con éxito! (id " + nueva.getId() + ")");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        listar();
        Long id = consola.leerLong("Ingrese el ID de la categoría a editar: ");
        try {
            Categoria actual = categoriaService.buscarPorId(id);
            String nombre = consola.leerTexto("Nuevo nombre [" + actual.getNombre() + "]: ", true);
            if (nombre.isBlank()) nombre = actual.getNombre();
            String desc = consola.leerTexto("Nueva descripción [" + actual.getDescripcion() + "]: ", true);
            if (desc.isBlank()) desc = actual.getDescripcion();

            categoriaService.editar(id, nombre, desc);
            System.out.println("¡Categoría actualizada!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        listar();
        Long id = consola.leerLong("Ingrese el ID de la categoría a eliminar: ");
        if (!consola.confirmar("¿Está seguro de dar de baja lógica esta categoría? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            categoriaService.eliminar(id);
            System.out.println("¡Categoría dada de baja con éxito!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
