package UI;

import entities.Usuario;
import enums.Rol;
import service.UsuarioService;
import java.util.List;

public class UsuarioMenu {
    private UsuarioService service;

    public UsuarioMenu(UsuarioService service) {
        this.service = service;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Registrar Usuario");
            System.out.println("3. Editar Usuario");
            System.out.println("4. Eliminar Usuario");
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
        List<Usuario> lista = service.listarUsuarios();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crear() throws Exception {
        String nombre = ConsolaHelper.leerCadena("Nombre: ");
        String apellido = ConsolaHelper.leerCadena("Apellido: ");
        String mail = ConsolaHelper.leerCadena("Mail (Único): ");
        String celular = ConsolaHelper.leerCadena("Celular: ");
        String pass = ConsolaHelper.leerCadena("Contraseña: ");
        
        System.out.println("Seleccione Rol: 1. ADMIN | 2. USUARIO");
        int rSel = ConsolaHelper.leerEntero("Opción: ");
        Rol rol = (rSel == 1) ? Rol.ADMIN : Rol.USUARIO;

        service.crearUsuario(nombre, apellido, mail, celular, pass, rol);
        System.out.println("¡Usuario registrado exitosamente!");
    }

    private void editar() throws Exception {
        listar();
        long id = ConsolaHelper.leerEntero("Ingrese ID del usuario a modificar: ");
        String nombre = ConsolaHelper.leerCadena("Nuevo Nombre: ");
        String apellido = ConsolaHelper.leerCadena("Nuevo Apellido: ");
        String mail = ConsolaHelper.leerCadena("Nuevo Mail: ");
        String celular = ConsolaHelper.leerCadena("Nuevo Celular: ");
        String pass = ConsolaHelper.leerCadena("Nueva Contraseña: ");
        
        System.out.println("Seleccione nuevo Rol: 1. ADMIN | 2. USUARIO");
        int rSel = ConsolaHelper.leerEntero("Opción: ");
        Rol rol = (rSel == 1) ? Rol.ADMIN : Rol.USUARIO;

        service.editarUsuario(id, nombre, apellido, mail, celular, pass, rol);
        System.out.println("¡Datos actualizados!");
    }

    private void eliminar() throws Exception {
        listar();
        long id = ConsolaHelper.leerEntero("Ingrese ID del usuario a eliminar: ");
        if (ConsolaHelper.leerConfirmacion("¿Proceder con la baja lógica?")) {
            service.eliminarUsuario(id);
            System.out.println("¡Usuario inhabilitado!");
        }
    }
}
