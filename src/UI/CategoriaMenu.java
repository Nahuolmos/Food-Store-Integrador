package UI;

import entities.Categoria;
import service.CategoriaService;
import java.util.List;

public class CategoriaMenu{
    private CategoriaService service;

    public CategoriaMenu(CategoriaService service) {
        this.service = service;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar Categorías");
            System.out.println("2. Crear Categoría");
            System.out.println("3. Editar Categoría");
            System.out.println("4. Eliminar Categoría");
            System.out.println("0. Volver");
            opcion = ConsolaHelper.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listar();
                    case 2 -> crear();
                    case 3 -> editar();
                    case 4 -> eliminar();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listar() {
        List<Categoria> lista = service.listarCategorias();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crear() throws Exception {
        String nombre = ConsolaHelper.leerCadena("Ingrese nombre de la categoría: ");
        String desc = ConsolaHelper.leerCadena("Ingrese descripción: ");
        service.crearCategoria(nombre, desc);
        System.out.println("¡Categoría creada con éxito!");
    }

    private void editar() throws Exception {
        listar();
        long id = ConsolaHelper.leerEntero("Ingrese el ID de la categoría a editar: ");
        String nombre = ConsolaHelper.leerCadena("Ingrese nuevo nombre: ");
        String desc = ConsolaHelper.leerCadena("Ingrese nueva descripción: ");
        service.editarCategoria(id, nombre, desc);
        System.out.println("¡Categoría actualizada!");
    }

    private void eliminar() throws Exception {
        listar();
        long id = ConsolaHelper.leerEntero("Ingrese el ID de la categoría a eliminar: ");
        if (ConsolaHelper.leerConfirmacion("¿Está seguro de dar de baja lógica esta categoría?")) {
            service.eliminarCategoria(id);
            System.out.println("¡Categoría dada de baja con éxito!");
        }
    }
}
