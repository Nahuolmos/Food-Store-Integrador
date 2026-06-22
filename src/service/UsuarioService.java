package service;
 
import exceptions.EntityNotFoundException;
import DAO.UsuarioDAO;
import entities.Usuario;
import enums.Rol;
import java.util.List;
 
public class UsuarioService {
 
    private final UsuarioDAO usuarioDAO;
 
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
 
    public List<Usuario> listar() {
        return usuarioDAO.findAllActive();
    }
 
    public Usuario crear(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) {
        validarNombreNoVacio(nombre, "nombre");
        validarNombreNoVacio(apellido, "apellido");
        validarNombreNoVacio(celular, "celular");
        validarFormatoMail(mail);
 
        if (usuarioDAO.existeMail(mail)) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el mail: " + mail);
        }
 
        Usuario nuevo = new Usuario(nombre, apellido, mail, celular, contrasena, rol);
        return usuarioDAO.save(nuevo);
    }
 
    public Usuario buscarPorId(Long id) throws EntityNotFoundException {
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null || usuario.isEliminado()) {
            throw new EntityNotFoundException("No se encontró un usuario activo con id: " + id);
        }
        return usuario;
    }
 
    public void actualizar(Usuario usuario) throws EntityNotFoundException {
        buscarPorId(usuario.getId());
        validarNombreNoVacio(usuario.getNombre(), "nombre");
        validarNombreNoVacio(usuario.getApellido(), "apellido");
        validarNombreNoVacio(usuario.getCelular(), "celular");
        validarFormatoMail(usuario.getMail());
        usuarioDAO.update(usuario);
    }
 
    public void eliminar(Long id) throws EntityNotFoundException {
        buscarPorId(id);
        usuarioDAO.softDelete(id);
    }
 
    public boolean existeMail(String mail) {
        return usuarioDAO.existeMail(mail);
    }
 
    private void validarNombreNoVacio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede estar vacío.");
        }
    }
 
    private void validarFormatoMail(String mail) {
        if (mail == null || mail.isBlank()) {
            throw new IllegalArgumentException("El mail no puede estar vacío.");
        }
        if (!mail.contains("@") || !mail.contains(".")) {
            throw new IllegalArgumentException("El mail no tiene un formato válido: " + mail);
        }
    }
}
