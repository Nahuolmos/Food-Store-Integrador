package UI;
 
import exceptions.EntityNotFoundException;
import entities.Usuario;
import enums.Rol;
import java.util.List;
import service.UsuarioService;
 
public class UsuarioMenu {
    private final UsuarioService usuarioService;
    private final ConsolaHelper consola;
 
    public UsuarioMenu(UsuarioService usuarioService, ConsolaHelper consola) {
        this.usuarioService = usuarioService;
        this.consola = consola;
    }
 
    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Usuarios ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
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
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        usuarios.forEach(System.out::println);
    }
 
    private void crear() {
        System.out.println("\n--- Crear Usuario ---");
 
        String nombre = consola.leerTexto("Nombre: ");
        String apellido = consola.leerTexto("Apellido: ");
 
        String mail = null;
        boolean mailValido = false;
        while (!mailValido) {
            mail = consola.leerTexto("Mail: ");
            if (!mail.contains("@") || !mail.contains(".")) {
                System.out.println("Formato de mail inválido. Intente nuevamente.");
                continue;
            }
            if (usuarioService.existeMail(mail)) {
                System.out.println("El mail ya está registrado. Ingrese otro.");
                continue;
            }
            mailValido = true;
        }
 
        String celular = consola.leerTexto("Celular: ");
        String contrasena = consola.leerTexto("Contraseña: ");
        Rol rol = consola.leerRol();
 
        try {
            Usuario nuevo = usuarioService.crear(nombre, apellido, mail, celular, contrasena, rol);
            System.out.println("Usuario creado con id " + nuevo.getId() + ".");
        } catch (Exception e) {
            System.out.println("Error al crear el usuario: " + e.getMessage());
        }
    }
 
    private void editar() {
        listar();
        Long id = consola.leerLong("Ingrese el id del usuario a editar: ");
 
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
 
            System.out.println("Dejá vacío para mantener el valor actual.");
 
            String nombre = consola.leerTexto("Nombre [" + usuario.getNombre() + "]: ");
            if (!nombre.isBlank()) usuario.setNombre(nombre);
 
            String apellido = consola.leerTexto("Apellido [" + usuario.getApellido() + "]: ");
            if (!apellido.isBlank()) usuario.setApellido(apellido);
 
            String nuevoMail = consola.leerTexto("Mail [" + usuario.getMail() + "]: ");
            if (!nuevoMail.isBlank()) {
                if (!nuevoMail.contains("@") || !nuevoMail.contains(".")) {
                    System.out.println("Formato de mail inválido. No se actualizó el mail.");
                } else if (!nuevoMail.equals(usuario.getMail()) && usuarioService.existeMail(nuevoMail)) {
                    System.out.println("El mail ya está registrado. No se actualizó el mail.");
                } else {
                    usuario.setMail(nuevoMail);
                }
            }
 
            String celular = consola.leerTexto("Celular [" + usuario.getCelular() + "]: ");
            if (!celular.isBlank()) usuario.setCelular(celular);
 
            usuarioService.actualizar(usuario);
            System.out.println("Usuario actualizado correctamente.");
 
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
 
    private void eliminar() {
        listar();
        Long id = consola.leerLong("Ingrese el id del usuario a eliminar: ");
        if (!consola.confirmar("¿Confirma la eliminación del usuario " + id + "? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            usuarioService.eliminar(id);
            System.out.println("Usuario eliminado (baja lógica).");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
